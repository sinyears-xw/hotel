package com.jiuzhe.hotel.service;

import java.util.Map;

/**
 * @Description:统计功能
 * @author:郑鹏宇
 * @date: 2018/5/22
 */
public interface CountService {
    Map<String, String> getIndexCount(String merchantId, String storeId);

    Map getManageCountInfo(String storeId);

}
