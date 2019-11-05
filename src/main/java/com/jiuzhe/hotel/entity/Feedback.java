package com.jiuzhe.hotel.entity;

/**
 * @Description:用户反馈模型
 * @author:张磊
 * @date:2018/4/13
 */
public class Feedback {
    /**
     * id
     */
    private int id;

    /**
     * 用户id
     */
    private String user_id;

    /**
     * 反馈内容
     */
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
