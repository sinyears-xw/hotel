package com.jiuzhe.hotel.service.impl;

import com.jiuzhe.hotel.dao.AdvertisementDao;
import com.jiuzhe.hotel.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @name: AdvertisementServiceImpl
 * @author: lucifinier
 * @date: 2018/5/4 18:37
 * @description: TODO
 */
@Service
public class AdvertisementServiceImpl implements AdvertisementService {
    @Autowired
    private AdvertisementDao advertisementDao;

    @Override
    public List<String> getAllAdImgUrl() {
        return advertisementDao.getAllAdImgUrl();
    }
}
