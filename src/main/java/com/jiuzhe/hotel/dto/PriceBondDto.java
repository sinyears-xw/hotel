package com.jiuzhe.hotel.dto;

/**
 * @Description:
 * @author:郑鹏宇
 * @date 2018/8/21/021
 */
public class PriceBondDto {
    private String skuId;
    private Integer roomBond;
    private Integer roomPrice;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public Integer getRoomBond() {
        return roomBond;
    }

    public void setRoomBond(Integer roomBond) {
        this.roomBond = roomBond;
    }

    public Integer getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(Integer roomPrice) {
        this.roomPrice = roomPrice;
    }
}
