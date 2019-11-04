package com.jiuzhe.hotel.constants;

/**
 * @Description:发送短信的状态码
 * @author:郑鹏宇
 * @date:2018/5/25
 */
public enum SendMessageEnum {
    SUCCESS("成功", 0), FAILED("失败", -1), MINUTE_LIMIT("分钟级限制", 3), HOUR_LIMIT("小时级限制", 4),DAY_LIMIT("天级限制",5);
    // 成员变量
    private String desc;
    private int index;

    // 构造方法
    private SendMessageEnum(String desc, int index) {
        this.desc = desc;
        this.index = index;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
