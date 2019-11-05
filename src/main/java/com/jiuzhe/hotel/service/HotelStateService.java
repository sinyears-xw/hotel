package com.jiuzhe.hotel.service;

import com.jiuzhe.hotel.dto.HotelStateDto;
import com.jiuzhe.hotel.module.HotelStataQuery;

/**
 * @Description:房态的service
 * @author:郑鹏宇
 * @date: 2018/5/30
 */
public interface HotelStateService {

    HotelStateDto getHotelState(HotelStataQuery query);
}
