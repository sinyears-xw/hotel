package com.jiuzhe.hotel.module;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description:手机验证模型
 * @author:yangyu
 * @date: 2018/4/27
 */
@ApiModel(value="telephoneCheckQuery",description="手机验证模型")
public class PhoneLoginQuery {
    @ApiModelProperty(value="登陆手机号",example = "15172298516")
    private String phone;

    @ApiModelProperty(value="手机验证码",example = "656235")
    private String messageCode;

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
