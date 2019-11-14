package com.jiuzhe.hotel.dao.mapper;

import com.jiuzhe.hotel.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LoginMapper {
    @Select("SELECT user_id id,phone FROM account where phone = #{phone} ")
    User getUserByPhone(String phone);

    @Insert("INSERT INTO account (user_id,phone) VALUES (#{id},#{phone})")
    int saveIdPhone(User user);
}
