package com.jiuzhe.hotel.dao.mapper;

import com.jiuzhe.hotel.entity.HotelSku;
import com.jiuzhe.hotel.module.SearchQuery;
import com.jiuzhe.hotel.utils.StringUtil;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Description:
 * @author:郑鹏宇
 * @date: 2018/6/7
 */
public class SkuSearchQueryProvider {
    public String getHotels(SearchQuery searchQueryEntity) {
        String a = new SQL() {
            {
                SELECT("a.id,d.lng,d.lat,a.room_status defStatus, d.storePic storePic, d.ReceptionPic receptionPic, a.room_price defPrice ,c.listing_price price,a.score," +
                        "a.address,b.name roomType,d.name skuName,a.room_no roomNo, a.imgurls imgs,a.store_id storeId,a.layout_id layoutId," +
                        "b.name layName,b.wifi layWifi,b.bedroom layBedroom,b.bed layBed,b.toilet layToilet,b.piclist laypicList,b.count laycount");
                FROM("hotel_sku a ");
                LEFT_OUTER_JOIN("hotel_sku_layout b on a.layout_id = b.id");
                LEFT_OUTER_JOIN("hotel_sku_listing_price c ON (a.id = c.sku_id AND c.listing_date = #{startDate})");
                LEFT_OUTER_JOIN("store d on a.store_id = d.id");
                if (null != searchQueryEntity.getBeginLng()) {
                    WHERE("d.lng >= #{beginLng}");
                }
                if (null != searchQueryEntity.getEndLng()) {
                    WHERE("d.lng <= #{endLng}");
                }
                if (null != searchQueryEntity.getBeginLat()) {
                    WHERE("d.lat >= #{beginLat}");
                }
                if (null != searchQueryEntity.getEndLat()) {
                    WHERE("d.lat <= #{endLat}");
                }
                GROUP_BY("a.id");
            }
        }.toString();
        System.out.printf(a);
        return a;
    }

    public String getRecommendHotel(HotelSku hotelSku, Integer gist) {
        return new SQL() {{
            SELECT("a.merchant_id merchantId, s.storePic storePic, " +
                    "s.ReceptionPic receptionPic,a.city_name cityName," +
                    "a.area, a.score,a.weight,a.address, a.id, " +
                    "a.room_price roomPrice, a.room_type roomType, a.sku_name skuName," +
                    " a.room_no roomNo, a.imgurls imgUrls,b.piclist laypicList");
            FROM("hotel_sku a ");
            LEFT_OUTER_JOIN("hotel_sku_layout b on a.layout_id = b.id");
            LEFT_OUTER_JOIN("store s on b.store_id = s.id");
            WHERE("a.room_status = 1");
            if (!StringUtil.isEmptyOrNull(hotelSku.getCityName())) {
                String cityName = hotelSku.getCityName();
                WHERE("a.city_name = " + "'" + cityName + "' ");
            }
            if (!StringUtil.isEmptyOrNull(hotelSku.getArea())) {
                String area = (hotelSku.getArea());
                WHERE("a.area =" + "'" + area + "' ");
            }
            GROUP_BY("a.id");
            if (0 == gist) {
                ORDER_BY("a.weight desc");
            } else {
                ORDER_BY("a.score desc");
            }
        }}.toString();
    }


}
