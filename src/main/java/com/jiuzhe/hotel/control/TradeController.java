package com.jiuzhe.hotel.control;

import com.jiuzhe.hotel.constants.RtCodeConstant;
import com.jiuzhe.hotel.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
public class TradeController {
    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    IdService idService;
    @Autowired
    AlipayService alipayService;

    @Autowired
    SqlService sqlService;

    @Autowired
    TradeService tradeService;

    @Autowired
    AccountService accountService;

    private Map<String, String> getRequestParam(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        return params;
    }

    //充值
    @PostMapping("/deposit")
    public Map deposit(@RequestBody Map param) {
        try {
            return tradeService.deposit(param);
        } catch (Exception e) {
            return RtCodeConstant.getResult("-1");
        }
    }

    //充值成功后调用
    @PostMapping("/webhook/alipay/deposit")
    public String aliwebhookdeposit(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> params = getRequestParam(request);
            logger.info(params);
            if (alipayService.rsaCheck(params)) {
                if (params.get("trade_status").equals("TRADE_SUCCESS")) {
                    long amount = Math.round(Double.parseDouble(params.get("total_amount")) * 100);
                    tradeService.updateDepositStatus(params.get("out_trade_no"), amount);
                }
            } else {
                logger.error("rsaCheck Failed:" + params.toString());
                return "failed";
            }
            return "success";
        } catch (Exception e) {
            logger.error(e);
            return "failed";
        }
    }


    /**
     * Description:提现
     * Author:
     * Date: 2019/4/12 10:53
     */
    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public Map withdraw(@RequestBody Map parms) {
        try {
            String userId = parms.get("userId").toString();
            String withdrewAmount = parms.get("withdrewAmount").toString();
            String payPassword = parms.get("passwd").toString();
            String channel = parms.get("channel").toString();
            String description = parms.get("description").toString();

            return tradeService.withdraw(userId, withdrewAmount, payPassword, channel, description);
        } catch (Exception e) {
            return RtCodeConstant.getResult("-1");
        }

    }


}
