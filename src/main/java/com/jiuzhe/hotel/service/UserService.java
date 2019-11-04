package com.jiuzhe.hotel.service;

import com.jiuzhe.hotel.dto.UserDto;
import com.jiuzhe.hotel.module.PhoneCheckQuery;
import com.jiuzhe.hotel.module.UserQuery;


/**
 * @Description:用户的Service
 * @author:郑鹏宇
 * @date: 2018/3/28
 */
public interface UserService {

    UserDto getUserById(String id);

    UserDto getUserByPhone(String phone);

    int updateUserPhone(String id, String phone);

    int updateUser(UserQuery userQuery);

    Integer checkOldPhone(PhoneCheckQuery query);

    Integer checkNewPhone(PhoneCheckQuery query);

    Boolean upNewPhone(String id);

}
