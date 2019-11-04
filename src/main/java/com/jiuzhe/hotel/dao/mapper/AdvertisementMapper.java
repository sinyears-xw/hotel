package com.jiuzhe.hotel.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @name: AdvertisementDao
 * @author: lucifinier
 * @date: 2018/5/4 18:32
 * @description: TODO
 */
@Mapper
@Repository
public interface AdvertisementMapper {
    @Select("select img_url as imgUrl from jzt_advertisement where is_display = 1")
    List<String> getAllAdImgUrl();
}
