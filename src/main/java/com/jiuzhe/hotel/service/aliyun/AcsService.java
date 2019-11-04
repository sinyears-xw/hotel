package com.jiuzhe.hotel.service.aliyun;

public interface AcsService {
    Integer sendValidateCode(String phone, String code, String id);
}
