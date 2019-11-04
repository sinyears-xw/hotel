package com.jiuzhe.hotel.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 日期处理
 * @author：yangyu
 * @date:2018/03/27
 */
public class DateUtil {
    private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
    private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");
    private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     * @author:yangyu
     * @date:2018/03/27
     * @return
     */
    public static String getTime() {
        return sdfTime.format(new Date());
    }
    /**
     * 获取YYYY-MM-DD格式
     * @return
     */
    public static String getDay() { return sdfDay.format(new Date()); }
    /**
     * 获取该天后的YYYY-MM-DD
     * @return
     */

    /**
     * 格式化日期
     * @author:yangyu
     * @date:2018/03/27
     * @return
     */
    public static Date fomatDate(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static LocalDate stringToLocalDate(String date){
        if(DateUtil.isValidDate(date)){
            Date d = DateUtil.fomatDate(date);
            //return
        }else{}
        return null;

    }

    /**
     * @Title: compareDate
     * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
     * @param s
     * @param e
     * @return boolean
     * @throws
     * @author fh
     */
    public static boolean compareDate(String s, String e) {
        if(fomatDate(s)==null||fomatDate(e)==null){
            return false;
        }
        return fomatDate(s).getTime() >=fomatDate(e).getTime();
    }

    /**
     * 校验日期是否合法
     * @author:yangyu
     * @date:2018/03/27
     * @return
     */
    public static boolean isValidDate(String s) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fmt.parse(s);
            return true;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }

    /**
     * 通过给定的时间段获取该段 的连续日期
     * @return
     */
    public static List<String> getDate(String startDate,int days){
        List<String> dates = new ArrayList<>();
          for(int i = 0;i<=days;++i){
              LocalDate start = LocalDate.parse(startDate);
              start = start.plusDays(i);
              String strDate = start.toString();
               dates.add(strDate);
           }
        return dates;
    }
}
