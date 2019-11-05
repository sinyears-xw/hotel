package com.jiuzhe.hotel.service.impl;

import com.jiuzhe.hotel.dao.RegionDao;
import com.jiuzhe.hotel.entity.HotelRegion;
import com.jiuzhe.hotel.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @author:郑鹏宇
 * @date: 2018/6/13
 */
@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    RegionDao dao;

    public List<HotelRegion> getAllRegion() {
        return dao.getAllRegion();
    }
}
