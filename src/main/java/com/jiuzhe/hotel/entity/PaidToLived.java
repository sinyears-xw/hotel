package com.jiuzhe.hotel.entity;

import com.jiuzhe.hotel.constants.OrderStatusEnum;

/**
 * @Description:自动将支付状态变成入住状态的类
 * @author:郑鹏宇
 * @date 2018/7/3/003
 */
public class PaidToLived {
    private String orderId;

    private String merchantId;

    private String userId;

    private Integer paid = OrderStatusEnum.PAID.getIndex();

    private Integer lived = OrderStatusEnum.LIVED.getIndex();

    public Integer getLived() {
        return lived;
    }

    public void setLived(Integer lived) {
        this.lived = lived;
    }

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
