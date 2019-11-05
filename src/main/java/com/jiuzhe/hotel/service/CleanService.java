package com.jiuzhe.hotel.service;

import com.jiuzhe.hotel.module.CleanQuery;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author:郑鹏宇
 * @date 2018/8/13/013
 */
public interface CleanService {
    Map getCleanInfo(List<String> storeIds);
    Map changRoomStaus(CleanQuery query);
}
