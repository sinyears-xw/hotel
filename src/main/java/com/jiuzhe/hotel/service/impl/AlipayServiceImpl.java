package com.jiuzhe.hotel.service.impl;

import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.jiuzhe.hotel.config.AlipayConfig;
import com.jiuzhe.hotel.constants.RtCodeConstant;
import com.jiuzhe.hotel.service.AlipayService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class AlipayServiceImpl implements AlipayService {
    private Log logger = LogFactory.getLog(this.getClass());

    public Map getOrder(String outtradeno, double amount, String body, String subject, String notify_url, boolean credit_forbidden) {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

        model.setBody(body);
        model.setSubject(subject);
        model.setOutTradeNo(outtradeno);
        model.setTimeoutExpress("30m");
        model.setTotalAmount(String.valueOf(amount));
        model.setProductCode("QUICK_MSECURITY_PAY");
        if (credit_forbidden)
            model.setDisablePayChannels("creditCard,credit_group");
        request.setBizModel(model);
        request.setNotifyUrl(notify_url);
        try {
            AlipayTradeAppPayResponse response = AlipayConfig.alipayClient.sdkExecute(request);
            return RtCodeConstant.getResult("0", response.getBody());
        } catch (Exception e) {
            logger.error(e);
            return RtCodeConstant.getResult("30002");
        }
    }

    public Map doTrans(String params) {
        // logger.info(params);
        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        request.setBizContent(params);
        try {
            AlipayFundTransToaccountTransferResponse response = AlipayConfig.alipayClient.execute(request);
            if (response.isSuccess()) {
                return RtCodeConstant.getResult("30003");
            } else {
                return RtCodeConstant.getResult("30004", response.getSubMsg());
            }
        } catch (Exception e) {
            logger.error(e);
            return RtCodeConstant.getResult("30002");
        }
    }

    public boolean rsaCheck(Map<String, String> params) {
        try {
            boolean flag = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
            return flag;
//            return true;
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }
}

