package com.jiuzhe.hotel.entity;

/**
 * @name: Advertisement
 * @author: lucifinier
 * @date: 2018/5/4 18:30
 * @description: TODO
 */
public class Advertisement {
    /**
     * id
     */
    private int id;

    /**
     * 用户id
     */
    private String imgUrl;

    /**
     * 反馈内容
     */
    private Boolean isDisplay;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Boolean getDisplay() {
        return isDisplay;
    }

    public void setDisplay(Boolean display) {
        isDisplay = display;
    }
}
