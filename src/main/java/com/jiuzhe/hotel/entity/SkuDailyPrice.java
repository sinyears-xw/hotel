package com.jiuzhe.hotel.entity;

import com.jiuzhe.hotel.utils.StringUtil;

import java.time.LocalDate;

/**
 * @Description:放盘价格
 * @author:郑鹏宇
 * @date: 2018/5/9
 */
public class SkuDailyPrice {

    private String id = StringUtil.get32UUID();

    /**
     * 房间id
     */
    private String skuId;

    /**
     *放盘日期
     */
    private LocalDate listDate;
    /**
     * 放盘价格
     */
    private Integer listingPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public LocalDate getListDate() {
        return listDate;
    }

    public void setListDate(LocalDate listDate) {
        this.listDate = listDate;
    }

    public Integer getListingPrice() {
        return listingPrice;
    }

    public void setListingPrice(Integer listingPrice) {
        this.listingPrice = listingPrice;
    }
}
