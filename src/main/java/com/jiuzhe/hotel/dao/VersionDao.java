package com.jiuzhe.hotel.dao;

import com.jiuzhe.hotel.dao.mapper.VersionMapper;
import com.jiuzhe.hotel.entity.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @name: VersionDao
 * @author: lucifinier
 * @date: 2018/5/9 09:45
 * @description: TODO
 */
@Repository
public class VersionDao {
    @Autowired
    private VersionMapper mapper;

    public Version getLatestVersion(int devType) {
        return mapper.getLatestVersion(devType);
    }
}



