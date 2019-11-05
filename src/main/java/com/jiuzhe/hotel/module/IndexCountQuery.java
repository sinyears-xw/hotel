package com.jiuzhe.hotel.module;

/**
 * @Description:首页展示需要的查询条件
 * @author:郑鹏宇
 * @date 2018/8/14/014
 */
public class IndexCountQuery {

    private String merchantId;

    private String storeId;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
