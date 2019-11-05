package com.jiuzhe.hotel.service;

import java.util.Map;

public interface TradeService {
    Map deposit(Map param);

    Map withdraw(Map param);

    void updateDepositStatus(String outTradeNo, long amount);

    void upWithdrawStatus(String withdrawId, String status, String withdrewAmount);
}
