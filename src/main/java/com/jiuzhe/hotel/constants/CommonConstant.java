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

    public static final int ID_EMPTY = 100000;
    public static final int OUT_DATA_EMPTY = 100001;
    public static final int IN_DATA_ERR = 100002;
    public static final int TOO_MANY_ORDER = 100101;
    public static final int REPEAT_ORDER = 100102;
    public static final int PRICE_ERROR = 100103;
    public static final int PAID_CANCEL_TIME = 100104;
    public static final int RESERVER = 100108;
    //不是需要的状态
    public static final int ERR_STATUS = 100107;
    //冥等性判定
    public static final int EXISTED = 100105;
    //冥等性判定
    public static final int UNEXISTED = 100106;
    //参数为空
    public static final int QUERY = 201;

    public static final String OK = "OK";
    public static final String ERR = "Error";
    public static final String FAILED = "Failed";

    public static final String STATUS = "status";
}
