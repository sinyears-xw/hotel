package com.jiuzhe.hotel.dao.mapper;

import com.jiuzhe.hotel.entity.PaidToLived;
import com.jiuzhe.hotel.utils.StringUtil;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Description:
 * @author:郑鹏宇
 * @date: 2018/6/7
 */
public class HotelOrderProvider {

    public String changePaidTolived(PaidToLived dto) {
        return new SQL() {{
            UPDATE("hotel_order");
            SET("order_status = #{lived}");
            WHERE("start_date = CURRENT_DATE() and order_status = 3");
            if (!StringUtil.isEmptyOrNull(dto.getOrderId())) {
                WHERE("id = #{orderId}");
            }
            if (!StringUtil.isEmptyOrNull(dto.getMerchantId())) {
                WHERE("merchant_id = #{merchantId}");
            }
            if (!StringUtil.isEmptyOrNull(dto.getUserId())) {
                WHERE("user_id = #{userId}");
            }
        }}.toString();
    }


}
