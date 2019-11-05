package com.jiuzhe.hotel.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.jiuzhe.hotel.config.AlipayConfig;
import com.jiuzhe.hotel.constants.RtCodeConstant;
import com.jiuzhe.hotel.service.*;
import com.jiuzhe.hotel.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class TradeServiceImpl implements TradeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SqlService sqlService;

    @Autowired
    IdService idService;

    @Autowired
    AlipayService alipayService;

    private boolean checkId(String id, int type) {
        Map num = null;
        switch (type) {
            case 1:
                num = sqlService.init().select().table("deposit")
                        .column("count(1) num ")
                        .condition("id = ", id)
                        .queryMap();
                break;
            case 2:
                num = sqlService.init().select().table("")
                        .column("count(1) num ")
                        .condition("id = ", id)
                        .queryMap();
                break;
        }

        if (Integer.parseInt(num.get("num").toString()) <= 0)
            return true;

        return false;
    }


    //充值
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map deposit(Map param) {
        //获取用户信息
        String userId = param.get("userId").toString();
        String depositId = idService.uid();
        String body = param.get("body").toString();
        String subject = param.get("subject").toString();
        String channel = param.get("channel").toString();
        Double amount = Double.parseDouble(param.get("depositAmount").toString());
        Map userMap = getUserByUserId(userId, "deposit");
        Map order = null;
        if (null == userMap) {
            return RtCodeConstant.getResult("20006");
        }
        if (!checkId(depositId, 1))
            return RtCodeConstant.getResult("20002");

        if (amount <= 0) {
            return RtCodeConstant.getResult("20003", String.valueOf(amount));
        }
        sqlService.init().insert().table("deposit")
                .column("id").value(depositId)
                .column("userId").value(userId)
                .column("amount").valueI(String.valueOf(amount))
                .column("status").valueI(String.valueOf(1))
                .column("updt").value(LocalDateTime.now().toString())
                .modify();
        //调用支付接口
        switch (channel) {
            case "alipay":
                //使用阿里支付
                order = alipayService.getOrder(depositId + "_" + userId, amount / 100, body, subject, AlipayConfig.notify_url_deposit, false);
                System.out.println(order);
                if (!order.get("status").equals("0")) {
                    throw new RuntimeException();
                }
                break;

        }

//        return order;

        Map rs = new HashMap();
        rs.put("depositId", depositId + "_" + userId);
        rs.put("notify_url", AlipayConfig.notify_url_deposit);
        return RtCodeConstant.getResult("0", rs);
    }

    //提现
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map withdraw(Map param) {
        String userId = param.get("userId").toString();
        String withdrawId = param.get("id").toString();
        String channel = param.get("channel").toString();
        String description = param.get("description").toString();
        long withdrewAmount = Long.parseLong(param.get("withdrewAmount").toString());
        //校验用户信息
        Map userMap = getUserByUserId(userId, "withdraw");
        if (null == userMap) {
            upWithdrawStatus(withdrawId, "2", "0");
            return RtCodeConstant.getResult("20006");
        }
        //校验提现金额
        if (withdrewAmount < 0 || withdrewAmount > Long.parseLong(userMap.get("amount").toString())) {
            upWithdrawStatus(withdrawId, "2", "0");
            return RtCodeConstant.getResult("20005");
        }
        String aliparamString = "";

        int withdrew = 0;
        switch (channel) {
            case "alipay":
                //获取阿里云的账号和名称
                String aliAccount = userMap.get("aliAccount").toString();
                Map aliAccountMap = JsonUtil.str2Bean(aliAccount, Map.class);
                String payeeName = aliAccountMap.get("name").toString();
                String payeeAccount = aliAccountMap.get("account").toString();
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
                //进入提现，需要锁住提现状态，一次只能进行一次提现
                upUserWithdraw(userId, "0");
                Map rs = alipayService.doTrans(aliparamString);
                if (rs.get("status").equals("30003"))
                    withdrew = 1;

                break;
        }

        if (withdrew != 1) {
            //提现失败，提现金额为0
            upWithdrawStatus(withdrawId, "2", "0");
            return RtCodeConstant.getResult("-1");
        }
        //提现成功，返回给支付宝的实际金额
        upWithdrawStatus(withdrawId, "0", String.valueOf(withdrewAmount));
        //修改用户金额
        upUserAmount(userId, "-" + withdrewAmount);
        //释放提现权限
        upUserWithdraw(userId, "1");
        return RtCodeConstant.getResult("0");
    }


    //根据id，修改提现订单状态和具体提现金额
    public void upWithdrawStatus(String withdrawId, String status, String withdrewAmount) {
        sqlService.init().update()
                .table("withdraw")
                .column("status", "updt", "withdrewAmount")
                .value(status, LocalDateTime.now().toString(), withdrewAmount)
                .condition("id = ", withdrawId)
                .modify();
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

    //获取有权限存取的账户，如果有则返回，没有则是null
    private Map getUserByUserId(String userId, String depositOrWithdraw) {
        try {
            Map query = sqlService.init().select()
                    .table("user")
                    .column("*")
                    .condition("userId = ", userId)
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
                .table("user")
                .column("amount")
                .valueI("amount" + amount)
                .condition("userId = ", userId)
                .modify();
    }

    //修改用户表中提现权限
    private void upUserWithdraw(String userId, String withdraw) {
        sqlService.init().update()
                .table("user")
                .column("withdraw")
                .value(withdraw)
                .condition("userId = ", userId)
                .modify();
    }

}
