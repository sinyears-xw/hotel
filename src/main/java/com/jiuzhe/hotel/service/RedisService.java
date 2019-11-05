package com.jiuzhe.hotel.service;

public interface RedisService {

    public String getResult(String redisKey, String sql);
}
