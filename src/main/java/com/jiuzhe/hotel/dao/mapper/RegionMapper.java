package com.jiuzhe.hotel.dao.mapper;

import com.jiuzhe.hotel.entity.HotelRegion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @author:郑鹏宇
 * @date: 2018/6/13
 */
@Mapper
@Repository
public interface RegionMapper {

    @Select("SELECT id,city_name cityName,areas FROM hotel_region ")
    List<HotelRegion> getAllRegion();

}
