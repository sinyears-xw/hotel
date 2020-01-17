package com.jiuzhe.hotel.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.jiuzhe.hotel.config.AlipayConfig;
import com.jiuzhe.hotel.constants.RtCodeConstant;
import com.jiuzhe.hotel.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class TradeServiceImpl implements TradeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Value("${hotel.order.paied.cancel-time}")
    private int paidCanceltime;
    @Autowired
    SqlService sqlService;

    @Autowired
    IdService idService;

    @Autowired
    AlipayService alipayService;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    //充值
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map deposit(String userId, String body, String subject, String channel, String depositAmount) {
        Double amount = Double.parseDouble(depositAmount);
        Map userMap = jdbcTemplate.queryForMap(String.format("select user_id from account where user_id = '%s' and canDeposit = 1", userId));
        Map order = null;

        if (null == userMap || userMap.size() == 0) {
            return RtCodeConstant.getResult("20006");
        }

        if (amount <= 0) {
            return RtCodeConstant.getResult("20003", String.valueOf(amount));
        }

        String depositId = idService.uid();
        sqlService.init().insert().table("deposit")
                .column("id").value(depositId)
                .column("user_id").value(userId)
                .column("amount").valueI(String.valueOf(amount))
                .column("status").valueI(String.valueOf(1))
                .column("updt").value(LocalDateTime.now().toString())
                .modify();
        //调用支付接口
        switch (channel) {
            case "alipay":
                //使用阿里支付
                order = alipayService.getOrder(depositId + "_" + userId, amount / 100, body, subject, AlipayConfig.notify_url_deposit, false);
                logger.info(order.toString());
                if (!order.get("status").equals("0")) {
                    throw new RuntimeException();
                }
                break;

        }

        Map rs = new HashMap();
        rs.put("depositId", depositId + "_" + userId);
        rs.put("notify_url", AlipayConfig.notify_url_deposit);
        return RtCodeConstant.getResult("0", rs);
    }

    //提现
    public Map withdraw(String userId, String withdrawAmount, String payPassword, String channel, String description) {
        long withdrewAmount = Long.parseLong(withdrawAmount);
        //校验用户信息
        Map userMap = getUserByUserId(userId, "canWithdrawn");
        if (null == userMap || userMap.get("aliName") == null || userMap.get("aliAccount") == null) {
            return RtCodeConstant.getResult("20007");
        }

        String storedPasswd = userMap.get("passwd").toString();
        if (null == storedPasswd || "".equals(storedPasswd))
            return RtCodeConstant.getResult("20008");

        if (!payPassword.equals(storedPasswd))
            return RtCodeConstant.getResult("20009");

        //校验提现金额
        if (withdrewAmount < 0 || withdrewAmount > Long.parseLong(userMap.get("available_balance").toString())) {
            return RtCodeConstant.getResult("20005");
        }

        int withdrew = 0;
        String aliparamString;
        String withdrawId = idService.uid();
        upUserWithdraw(userId, "0");

        try {
            switch (channel) {
                case "alipay":
                    //获取阿里云的账号和名称
                    String payeeName = userMap.get("aliName").toString();
                    String payeeAccount = userMap.get("aliAccount").toString();
                    Map aliparam = new HashMap<String, String>();
                    aliparam.put("out_biz_no", withdrawId);
                    aliparam.put("payee_type", AlipayConfig.payee_type);
                    aliparam.put("payer_show_name", AlipayConfig.payer_show_name);
                    aliparam.put("payee_account", payeeAccount);
                    aliparam.put("payee_real_name", payeeName);
                    aliparam.put("amount", String.valueOf(((double) withdrewAmount / 100)));
                    aliparam.put("remark", description);
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        aliparamString = mapper.writeValueAsString(aliparam);
                    } catch (Exception e) {
                        return RtCodeConstant.getResult("-1");
                    }
                    Map rs = alipayService.doTrans(aliparamString);
                    if (rs.get("status").equals("30003"))
                        withdrew = 1;

                    break;

                default:
                    return RtCodeConstant.getResult("20010");
            }

            return withdrawTrans(withdrew, withdrawId, userId, withdrawAmount);
        } finally {
            upUserWithdraw(userId, "1");
        }
    }


    @Transactional
    public Map withdrawTrans(int withdrew, String withdrawId, String userId, String withdrewAmount) {
        if (withdrew != 1) {
            insertWithdraw(withdrawId, userId, withdrewAmount, "4");
            return RtCodeConstant.getResult("-1");
        }
        insertWithdraw(withdrawId, userId, withdrewAmount, "3");
        upUserAmount(userId, "-" + withdrewAmount);
        return RtCodeConstant.getResult("0");
    }


    //根据id，修改充值订单状态和具体提现金额,同时需要将用户金额加上充值金额
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateDepositStatus(String outTradeNo, long amount) {
        String depositId = outTradeNo.split("_")[0];
        String userId = outTradeNo.split("_")[1];
        sqlService.init().update()
                .table("deposit")
                .column("status", "paidAmount", "updt")
                .value("0", String.valueOf(amount), LocalDateTime.now().toString())
                .condition("id = ", depositId)
                .modify();
        //修改用户金额
        upUserAmount(userId, "+" + amount);

    }

    @Transactional
    public Map charge(String userId, String orderId, String payPassword) {
        String sql = String.format("select available_balance, passwd " +
                "from account where disable = 0 and user_id = '%s' for update", userId);
        Map account = jdbcTemplate.queryForMap(sql);
        if (account == null || account.size() == 0) {
            return RtCodeConstant.getResult("10001");
        }

        long money = Long.parseLong(account.get("available_balance").toString());
        if (money < 0)
            return RtCodeConstant.getResult("40001");

        String passwd = account.get("passwd").toString();
        if (passwd == null)
            return RtCodeConstant.getResult("20008");
        else if (!passwd.equals(payPassword))
            return RtCodeConstant.getResult("10007");

        sql = String.format("select sku_price + sku_bond fee, sku_bond, merchant_id, cast(order_status as signed) as order_status " +
                "from hotel_order where id = '%s' for update", orderId);
        Map order = jdbcTemplate.queryForMap(sql);
        if (order == null || order.size() == 0)
            return RtCodeConstant.getResult("40002");


        int order_status = Integer.parseInt(order.get("order_status").toString());
        if (order_status != 1)
            return RtCodeConstant.getResult("40003");

        long fee = Long.parseLong(order.get("fee").toString());
        long sku_bond = Long.parseLong(order.get("sku_bond").toString());
        if (fee < 0 || sku_bond < 0)
            return RtCodeConstant.getResult("40001");
        if (money < fee)
            return RtCodeConstant.getResult("10006");

        String mid = order.get("merchant_id").toString();
        sql = String.format("select count(1) num from merchant_account where id = '%s' for update", mid);
        Map mAccount = jdbcTemplate.queryForMap(sql);
        if (Integer.parseInt(mAccount.get("num").toString()) != 1)
            return RtCodeConstant.getResult("10008");

        sql = String.format("update account set total_balance = total_balance - %d , available_balance = available_balance - %d where user_id = '%s'", fee, fee, userId);
        jdbcTemplate.update(sql);
        LocalDateTime paidCancelTime = LocalDateTime.now().plusMinutes(paidCanceltime);
        jdbcTemplate.update(String.format("update hotel_order set order_status =3 ,paid_cancel_time = '%s' where id = '%s'", paidCancelTime.toString(), orderId));
        jdbcTemplate.update(String.format("update merchant_account set profit = profit + %d , mortagage = mortagage + %d where id = '%s'", fee - sku_bond, sku_bond, mid));

        return RtCodeConstant.getResult("0");

    }


    //获取有权限存取的账户，如果有则返回，没有则是null
    private Map getUserByUserId(String userId, String depositOrWithdraw) {
        try {
            Map query = sqlService.init().select()
                    .table("account")
                    .column("*")
                    .condition("user_id = ", userId)
                    .end(" for update ")
                    .queryMap();
            if (null != query && "1".equals(query.get(depositOrWithdraw).toString())) {
                return query;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }


    //修改用户金额（提现为负，充值为正）
    private void upUserAmount(String userId, String amount) {
        sqlService.init().update()
                .table("account")
                .column("total_balance")
                .valueI("total_balance" + amount)
                .column("available_balance")
                .valueI("available_balance" + amount)
                .condition("user_id = ", userId)
                .modify();
    }

    //修改用户表中提现权限
    private void upUserWithdraw(String userId, String withdraw) {
        sqlService.init().update()
                .table("account")
                .column("canWithdrawn")
                .valueI(withdraw)
                .condition("user_id = ", userId)
                .modify();
    }

    private void insertWithdraw(String withdrawId, String userId, String withdrewAmount, String status) {
        sqlService.init().insert()
                .table("withdraw")
                .column("id", "user_id", "amount", "status")
                .value(withdrawId, userId, withdrewAmount, status)
                .modify();
    }

}
