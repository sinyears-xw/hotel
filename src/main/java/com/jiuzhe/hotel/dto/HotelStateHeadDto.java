package com.jiuzhe.hotel.dto;

import java.time.LocalDate;

/**
 * @Description:房态的头，包括日期，星期，剩余房间数量
 * @author:郑鹏宇
 * @date: 2018/5/30
 */
public class HotelStateHeadDto {
    /**
     * 日期（4-5）
     */
    private String date;

    /**
     * 剩余房间数量
     */
    private Integer freeNum;

    public static HotelStateHeadDto make(LocalDate date, Integer freeNum) {
        HotelStateHeadDto dto = new HotelStateHeadDto();
        dto.setDate(date.toString());
//        dto.setMonthDay(date.toString());
//        dto.setWeekDay(TimeUtil.getDayOfWeek(date));
//        dto.setMonthDay(TimeUtil.getMonthDay(date));
        dto.setFreeNum(freeNum);
        return dto;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getFreeNum() {
        return freeNum;
    }

    public void setFreeNum(Integer freeNum) {
        this.freeNum = freeNum;
    }
}
