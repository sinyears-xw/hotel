package com.jiuzhe.hotel.utils;

import java.util.List;

/**
 * @Description:List转化为String的工具
 * @author:郑鹏宇
 * @date: 2018/3/21
 */
public class ListToStringUtil {
    public static String listToString(List<String> list){
        if (list==null){
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        //第一个前面不拼接","
        for(String string :list) {
            if(first) {
                first=false;
            }else{
                result.append(",");
            }
            result.append(string);
        }
        return result.toString();

    }
}
