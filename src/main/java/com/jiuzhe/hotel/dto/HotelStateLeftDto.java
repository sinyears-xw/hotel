package com.jiuzhe.hotel.dto;

/**
 * @Description:房态左边的
 * @author:郑鹏宇
 * @date: 2018/6/1
 */
public class HotelStateLeftDto {

    private String skuId;

    private String skuName;

    private String roomNo;

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }
}
