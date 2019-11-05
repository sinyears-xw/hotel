package com.jiuzhe.hotel.constants;

import com.jiuzhe.hotel.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RtCodeConstant {
    private static Logger logger = LoggerFactory.getLogger(RtCodeConstant.class);

    public final static Map<String, String> rtcodeMessage = new HashMap<String, String>() {
        {
            put("-2", "查无数据");
            put("-1", "服务器异常");
            put("0", "成功");
            //登录
            put("10000", "手机号格式错误");
            put("10001", "该用户不存在");
            put("10002", "用户信息已过期，请重新登录");
            put("10003", "验证码错误");
            put("10004", "验证码已过期");
            put("10005", "权限不够");
            put("10006", "资金不足");

            put("20000", "数据修改失败，请重试");
            put("20001", "数据保存失败");
            put("20002", "重复充值");
            put("20003", "充值金额错误");
            put("20004", "重复提现");
            put("20005", "提现金额错误");
            put("20006", "无法充值");
            put("20007", "无法提现");
            put("20008", "请设置支付密码");
            put("20009", "支付密码错误");

            put("30001", "支付宝支付中");
            put("30002", "支付宝支付异常");
            put("30003", "支付宝支付成功");
            put("30004", "支付宝支付失败");
            put("30005", "支付宝参数错误");
        }
    };


    public static Map getResult(String rtcode, Object... values) {
        Map result = new HashMap();
        result.put("status", rtcode);
        int len = values.length;

        result.put("message", rtcodeMessage.get(rtcode));

        if (len == 1 && (values[0] instanceof Map)) {
            result.put("data", values[0]);
            return result;
        }
        List data = new ArrayList();
        for (int i = 0; i < len; i++) {
            Object value = values[i];
            objTransform(value, data);
        }
        result.put("data", data);
        return result;
    }


    public static Map getResult(String rtcode, List beans) {
        if (null == beans) {
            beans = new ArrayList();
        }
        Map result = new HashMap();
        List datas = new ArrayList();
        result.put("status", rtcode);
        result.put("message", rtcodeMessage.get(rtcode));

        for (int j = 0; j < beans.size(); j++) {
            Object value = beans.get(j);
            objTransform(value, datas);
        }

        result.put("data", datas);
        return result;
    }

    private static void objTransform(Object value, List datas) {
        if (value instanceof String ||
                value instanceof Integer ||
                value instanceof Long ||
                value instanceof Double ||
                value instanceof Boolean ||
                value instanceof Character)
            datas.add(value);
        else {
            Map temp = JsonUtil.obj2Map(value);
            if (temp != null)
                datas.add(temp);
        }
    }
}

