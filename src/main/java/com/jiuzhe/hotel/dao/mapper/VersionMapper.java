package com.jiuzhe.hotel.dao.mapper;

import com.jiuzhe.hotel.entity.Version;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @name: VersionMapper
 * @author: lucifinier
 * @date: 2018/5/9 09:34
 * @description: TODO
 */
@Mapper
@Repository
public interface VersionMapper {
    @Select("SELECT * FROM ios_version WHERE dev_type = #{devType} ORDER BY inner_ver DESC LIMIT 1")
    @Results({
            @Result(property = "devType", column = "dev_type"),
            @Result(property = "innerVer", column = "inner_ver"),
            @Result(property = "outVer", column = "out_ver"),
            @Result(property = "appUrl", column = "app_url"),
            @Result(property = "isForce", column = "is_force")
    })
    Version getLatestVersion(int devType);
}
