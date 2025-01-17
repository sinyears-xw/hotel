package com.jiuzhe.hotel.dao;

import com.jiuzhe.hotel.dao.mapper.SkuManageMapper;
import com.jiuzhe.hotel.dto.PriceBondDto;
import com.jiuzhe.hotel.entity.HotelSku;
import com.jiuzhe.hotel.entity.SkuDailyPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @Description:房东段管理
 * @author:郑鹏宇
 * @date: 2018/5/9
 */
@Repository
public class SkuManageDao {

    @Autowired
    SkuManageMapper mapper;

    /**
     * @Description:根据条件获取相关的房间
     * @author:郑鹏宇
     * @date:2018/5/10
     */
    public List<HotelSku> getSkuHotels(HotelSku hotelSku) {
        return mapper.getSkuHotels(hotelSku);
    }


    /**
     * @Description:房间是否存在
     * @author:郑鹏宇
     * @date:2018/5/15
     */
    public int checkSkuHotelNum(String skuId) {
        return mapper.checkSkuHotelNum(skuId);
    }

    /**
     * @Description:保存信息到hotel_sku表
     * @author:郑鹏宇
     * @date:2018/5/10
     */
    public int saveSkuHotel(HotelSku hotelSku) {
        return mapper.saveSkuHotel(hotelSku);
    }

    public int saveMoreSku(List<HotelSku> skuList) {
        return mapper.saveMoreSku(skuList);
    }

    public int upCount(int num, String layId) {
        return mapper.upCount(num, layId);
    }


    /**
     * @Description:删除房源
     * @author:郑鹏宇
     * @date:2018/5/11
     */
    public int deleteSku(String skuId) {
        return mapper.deleteSku(skuId);
    }

    /**
     * @Description:删除放盘价（通过skuId）
     * @author: 郑鹏宇
     * @date 2018/8/22/022
     */
    public int deletePriceBySkuId(String skuId) {
        return mapper.deleteListingPriceBySkuId(skuId);
    }

    public PriceBondDto getPriceAndBond(String skuId) {
        return mapper.getPriceAndBond(skuId);
    }

    /**
     * @Description:修改房源信息
     * @author:郑鹏宇
     * @date:2018/5/11
     */
    public int upSkuHotel(HotelSku hotelSku) {
        return mapper.upSkuHotel(hotelSku);
    }


    /**
     * @Description:批量修改房间状态和押金
     * @author:郑鹏宇
     * @date:2018/5/21
     */
    public int upAllSkuPrice(Integer roomBond, Integer roomPrice, Integer roomStatus, List<String> skuIds) {
        return mapper.upAllSkuPrice(roomBond, roomPrice, roomStatus, skuIds);
    }

    /**
     * @Description:批量删除放盘价格
     * @author:郑鹏宇
     * @date:2018/5/21
     */
    public int deleteListingPrice(List<String> skuIds, LocalDate startDate, LocalDate endDate) {
        return mapper.deleteListingPrice(skuIds, startDate, endDate);
    }

    /**
     * @Description:单个删除删除放盘价格
     * @author:郑鹏宇
     * @date:2018/5/21
     */
    public int deleteListingPrice(String skuId, LocalDate startDate, LocalDate endDate) {
        return mapper.deleteDailyPrice(skuId, startDate, endDate);
    }

    /**
     * @Description:批量添加放盘价格
     * @author:郑鹏宇
     * @date:2018/5/21
     */
    public int saveListingPrice(List<SkuDailyPrice> dailyPrices) {
        return mapper.saveListingPrice(dailyPrices);
    }

    public int deleteDailyPrice(String skuId, LocalDate begin, LocalDate end) {
        return mapper.deleteDailyPrice(skuId, begin, end);
    }


    /**
     * @Description:获取房间状态
     * @author:郑鹏宇
     * @date:2018/6/6
     */
    public Integer getRoomStaus(String skuId) {
        return mapper.getRoomStatus(skuId);
    }

    /**
     * @Description:修改房间状态
     * @author:郑鹏宇
     * @date:2018/6/6
     */
    public Integer upRoomStatus(String skuId, Integer roomStatus) {
        return mapper.upRoomStatus(skuId, roomStatus);
    }
}
