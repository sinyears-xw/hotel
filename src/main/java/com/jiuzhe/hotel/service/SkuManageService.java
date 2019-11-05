package com.jiuzhe.hotel.service;

import com.jiuzhe.hotel.dto.PriceBondDto;
import com.jiuzhe.hotel.entity.HotelSku;
import com.jiuzhe.hotel.entity.SkuSaveQuery;
import com.jiuzhe.hotel.module.DailyPriceQuery;
import com.jiuzhe.hotel.module.MannageSearchSkuQuery;
import com.jiuzhe.hotel.module.SkuQuery;
import com.jiuzhe.hotel.module.UpAllSkuPriceQuery;

import java.util.List;

/**
 * @Description:房东端房间管理
 * @author:郑鹏宇
 * @date: 2018/5/9
 */
public interface SkuManageService {

    List<HotelSku> getSkuDetails(MannageSearchSkuQuery query);

    void saveSku(SkuSaveQuery query);

    void upSku(SkuQuery query);

    PriceBondDto getPriceAndBond(String skuId);

    void deleteSku(String skuId, String layId);

    boolean checkSkuHotelNum(String skuId);

    void upAllSkuPrice(UpAllSkuPriceQuery query);

    List getSkuPriceBySkuId(String skuId);

    void deleteSkuPrice(DailyPriceQuery query);


    void upDurtyToSale(String skuId);

    Integer getRoomStatus(String skuId);
}
