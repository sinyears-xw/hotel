package com.jiuzhe.hotel.module;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description:
 * @author:郑鹏宇
 * @date: 2018/3/30
 */
@ApiModel(value="手机校验",description="手机校验模型Query")
public class PhoneCheckQuery {

    @ApiModelProperty(value="属性ID",example="1")
    private String id;

    @ApiModelProperty(value="手机号码",example="15172888464")
    private String phone;

    @ApiModelProperty(value="短信验证码",example="123456")
    private String messageCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

}
