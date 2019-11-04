package com.jiuzhe.hotel.dao;

import com.jiuzhe.hotel.dao.mapper.AdvertisementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @name: AdvertisementDao
 * @author: lucifinier
 * @date: 2018/5/4 18:32
 * @description: TODO
 */
@Repository
public class AdvertisementDao {
    @Autowired
    AdvertisementMapper advertisementMapper;

    public List<String> getAllAdImgUrl() {
        return advertisementMapper.getAllAdImgUrl();
    }
}
