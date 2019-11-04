package com.jiuzhe.hotel.utils;

import java.util.Random;

public class RandomUtil {
    private static Random random = new Random();

    public static String getMessageCode(){
        int a = (int)((Math.random()*9+1)*100000);
        return String.valueOf(a);
    }

    public static Integer getRandom(int max,int min){
        return random.nextInt(max)+min;
    }
}
