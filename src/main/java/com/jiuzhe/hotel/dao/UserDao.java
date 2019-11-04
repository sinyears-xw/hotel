package com.jiuzhe.hotel.dao;

import com.jiuzhe.hotel.dao.mapper.UserMapper;
import com.jiuzhe.hotel.dto.UserDto;
import com.jiuzhe.hotel.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    @Autowired
    UserMapper userMapper;

    public UserDto getUserById(String id){
        return userMapper.getUserById(id);
    }

    public  UserDto getUserByPhone(String phone){
        return userMapper.getUserByPhone(phone);
    }

    public User getUser(User user){
        return userMapper.getUser(user);
    }

    public int updateUser(User user){
        return userMapper.updateUser(user);
    }

    public int checkPhone(User user){
        return userMapper.checkPhone(user);
    }

    public int saveUser(User user){
        return userMapper.saveUser(user);
    }
}
