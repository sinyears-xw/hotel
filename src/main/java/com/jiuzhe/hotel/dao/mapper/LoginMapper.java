package com.jiuzhe.hotel.dao.mapper;

import com.jiuzhe.hotel.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LoginMapper {
    @Select("SELECT id,phone FROM jzt_user where phone = #{phone} ")
    User getUserByPhone(String phone);

    @Insert("INSERT INTO jzt_user (id,phone) VALUES (#{id},#{phone})")
    int saveIdPhone(User user);
}
