package com.jiuzhe.hotel.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @Description:校验相关的工具类
 * @author:郑鹏宇
 * @date: 2018/3/30
 */
public class CheckUtil {

    /**
    	 * @Description:手机号码的校验
    	 * @author:郑鹏宇
    	 * @date:2018/3/30
    	 */
    public static boolean isPhone( String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = compile("^[1][3,4,5,7,8][0-9]{9}$");
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
}
