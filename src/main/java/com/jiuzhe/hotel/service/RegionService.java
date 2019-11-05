package com.jiuzhe.hotel.service;

import com.jiuzhe.hotel.entity.HotelRegion;

import java.util.List;

/**
 * @Description:
 * @author:郑鹏宇
 * @date: 2018/6/13
 */
public interface RegionService {

    /**
     * @Description:获取所有的城市
     * @author:郑鹏宇
     * @date:2018/6/13
     */
    List<HotelRegion> getAllRegion();
}
