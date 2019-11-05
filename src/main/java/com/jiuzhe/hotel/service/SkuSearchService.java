package com.jiuzhe.hotel.service;

import com.jiuzhe.hotel.dto.SkuDetailDto;
import com.jiuzhe.hotel.entity.HotelSku;
import com.jiuzhe.hotel.entity.Search;
import com.jiuzhe.hotel.entity.SkuDetail;
import com.jiuzhe.hotel.module.RecommetQuery;
import com.jiuzhe.hotel.module.SearchQuery;
import com.jiuzhe.hotel.module.SkuDetailQuery;

import java.util.List;

/**
 * @Description:
 * @author:郑鹏宇
 * @date: 2018/4/6
 */
public interface SkuSearchService {


    /**
     * @Description:判定该id是否存在
     * @author:郑鹏宇
     * @date:2018/6/11
     */
    Boolean checkHotelNum(String skuId);

    /**
     * @Description:二次走数据库取酒店信息
     * @author:郑鹏宇
     * @date:2018/4/7
     */
    List<Search> getHotelItems(SearchQuery searchQuery);

    /**
     * @Description:点击地图图标展示预定酒店详情
     * @author:郑鹏宇
     * @date:2018/4/9
     */
    SkuDetailDto getSkuFacilitys(SkuDetailQuery skuDetailQuery);

    /**
     * @Description:通过skuId获取房间的所有信息
     * @author:郑鹏宇
     * @date:2018/6/8
     */
    SkuDetail getSkuDetailBySkuId(String skuId);

    /**
     * @Description:根据全中找房子
     * @author: 郑鹏宇
     * @date 2018/6/20/020
     */
    List<HotelSku> getRecommendHotel(RecommetQuery query);

    /**
     * @Description:从数据库总获取所有的图
     * @author: 郑鹏宇
     * @date 2018/8/16/016
     */
    List<String> getAllimgsFromDb();

    /**
    * @Description:
    * @Author: luan
    * @Date: 2018/11/7/007
    */
    String getPhoneBySkuId(String skuId);
}
