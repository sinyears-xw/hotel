package com.jiuzhe.hotel.dao;

import com.jiuzhe.hotel.dao.mapper.FeedbackMapper;
import com.jiuzhe.hotel.dto.OrderScoreDto;
import com.jiuzhe.hotel.entity.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FeedbackDao {
    @Autowired
    FeedbackMapper feedbackMapper;

    public void saveFeedback(Feedback feedback) {
        feedbackMapper.saveFeedback(feedback);
    }

    public List<OrderScoreDto> getOrderScoreByStoreId(String storeId) {
        return  feedbackMapper.getOrderScoreByStoreId(storeId);
    }

    public List<OrderScoreDto> getSkuScoreByStoreId(String storeId) {
        return  feedbackMapper.getSkuScoreByStoreId(storeId);
    }
}
