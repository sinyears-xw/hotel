package com.jiuzhe.hotel.dao;

import com.jiuzhe.hotel.constants.OrderStatusEnum;
import com.jiuzhe.hotel.dao.mapper.SchedulerMapper;
import com.jiuzhe.hotel.entity.HotelOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @Description:定时器任务
 * @author:郑鹏宇
 * @date: 2018/5/2
 */

@Repository
public class SchedulerDao {

    @Autowired
    SchedulerMapper schedulerMapper;

    public List<HotelOrder> getMsgUserGo(LocalDate endDate, Integer orderStatus) {
        return schedulerMapper.getMsgUserGo(endDate, orderStatus);
    }

    public void changPaidToLived() {
        schedulerMapper.changPaidToLived(OrderStatusEnum.PAID.getIndex(), OrderStatusEnum.LIVED.getIndex());
    }

    public void changLivedToApply() {
        schedulerMapper.changLivedToApply(OrderStatusEnum.LIVED.getIndex(), OrderStatusEnum.APPLY.getIndex());
    }

    public void changLivedToApplyByUserId(String userId) {
        schedulerMapper.changLivedToApplyByUserId(OrderStatusEnum.LIVED.getIndex(), OrderStatusEnum.APPLY.getIndex(), userId);
    }

}
