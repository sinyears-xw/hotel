package com.jiuzhe.hotel.service;

import java.util.Map;

public interface TradeService {
    Map deposit(String userId, String body, String subject, String channel, String depositAmount);

    Map withdraw(String userId, String withdrawAmount, String payPassword, String channel, String description);

    void updateDepositStatus(String outTradeNo, long amount);
}
