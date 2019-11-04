package com.jiuzhe.hotel.constants;

/**
 * @Description:
 * @author:郑鹏宇
 * @date: 2018/3/26
 */
public class CommonConstant {
    public static final Integer SUCCESS = 0;
    public static final Integer FAIL = -1;
    public static final Integer TOKEN_INVAIID = 200000;

    public static final String FAILED = "Failed";
    public static final String TOKEN_INVAIID_DESC = "token is not exits";
    /**
     * 用户不存在
     */
    public static final Integer USER_NOT_EXIST = 200105;

    /**
     * 用户存在
     */
    public static final Integer EXIST = 100105;
    /**
     * 原手机号错误
     */
    public static final Integer OLDPHONE_FAIL = 200103;
    /**
     * 新手机号错误
     */
    public static final Integer NEWPHONE_FAIL = 200104;
}
