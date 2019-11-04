package com.jiuzhe.hotel.dao;

import com.jiuzhe.hotel.dao.mapper.LoginMapper;
import com.jiuzhe.hotel.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDao {
    @Autowired
    LoginMapper loginMapper;

    public User getUserByPhone(String phone) {
        return loginMapper.getUserByPhone(phone);
    }

    public int saveIdPhone(User user) {
        return loginMapper.saveIdPhone(user);
    }
}