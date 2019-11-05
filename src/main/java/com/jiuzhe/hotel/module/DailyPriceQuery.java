package com.jiuzhe.hotel.module;

/**
 * @Description:
 * @author:郑鹏宇
 * @date: 2018/6/8
 */
public class DailyPriceQuery {
    private String startDate;

    private String endDate;

    private String skuId;


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }
}
