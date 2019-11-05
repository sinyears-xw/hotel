package com.jiuzhe.hotel.entity;

import java.time.LocalDate;

/**
 * @Description:
 * @author:郑鹏宇
 * @date: 2018/4/10
 */
public class SkuDetailDate {
    /**
     * 价格(单价)
     */
    private Integer listingPrice;
    /**
     * 每天的日期
     */
    private LocalDate listDate;

    public Integer getListingPrice() {
        return listingPrice;
    }

    public void setListingPrice(Integer listingPrice) {
        this.listingPrice = listingPrice;
    }

    public LocalDate getListDate() {
        return listDate;
    }

    public void setListDate(LocalDate listDate) {
        this.listDate = listDate;
    }
}
