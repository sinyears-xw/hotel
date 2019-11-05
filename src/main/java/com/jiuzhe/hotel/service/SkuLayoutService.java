package com.jiuzhe.hotel.service;

import com.jiuzhe.hotel.dao.SkuLayoutDao;

import java.util.Map;

public interface SkuLayoutService {
    public Map createSkuLayout(SkuLayoutDao layout);
    public Map queryAllSkuLayout(String id);
    public Map querySingleSkuLayout(String id);
    public Map updateSingleSkuLayout(String id, SkuLayoutDao layout);
    public Map removeSkuLayout(String id);
}
