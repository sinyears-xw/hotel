package com.jiuzhe.hotel.dao.mapper;

import com.jiuzhe.hotel.dto.UserDto;
import com.jiuzhe.hotel.entity.User;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

/**
 * @Description:用户的DAO
 * @author:郑鹏宇
 * @date: 2018/3/28
 */
@Mapper
@Repository
public interface UserMapper {

    @SelectProvider(type = UserProvider.class, method = "getUserById")
    UserDto getUserById(String id);

    @SelectProvider(type = UserProvider.class, method = "getUserByPhone")
    UserDto getUserByPhone(String phone);

    @SelectProvider(type = UserProvider.class, method = "getUser")
    User getUser(User user);

    @UpdateProvider(type = UserProvider.class, method = "updateUser")
    int updateUser(User user);

    @SelectProvider(type = UserProvider.class, method = "checkPhone")
    int checkPhone(User user);

    @InsertProvider(type = UserProvider.class, method = "saveUser")
    int saveUser(User user);
}
