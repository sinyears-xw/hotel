package com.jiuzhe.hotel.service;

import com.jiuzhe.hotel.entity.Version;

/**
 * @name: VersionService
 * @author: lucifinier
 * @date: 2018/5/9 09:48
 * @description: TODO
 */
public interface VersionService {
    Version getLatestVersion(int devType);
}
