package com.jiuzhe.hotel.constants;

/**
 * @name: DevTypeEnum
 * @author: lucifinier
 * @date: 2018/5/9 09:49
 * @description: TODO
 */
public enum DevTypeEnum {
    IOS("IOS", 1),
    ANDROID("安卓", 2),
    NULL("无",  3);

    // 成员变量
    private String desc;
    private int index;

    // 构造方法
    private DevTypeEnum(String desc, int index) {
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
