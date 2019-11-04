package com.jiuzhe.hotel.service;

import com.jiuzhe.hotel.dto.TakenkDto;
import com.jiuzhe.hotel.module.LoginQuery;

import java.util.Map;

/**
 * @Description:
 * @author:郑鹏宇
 * @date: 2018/4/11
 */
public interface LoginService {

    Map<Integer, String> checkLoginPhone(LoginQuery loginQuery);

    TakenkDto saveTaken(String id);

    TakenkDto autoLogin(String id);
}
