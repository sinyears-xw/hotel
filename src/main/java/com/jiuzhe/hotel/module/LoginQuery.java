package com.jiuzhe.hotel.module;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description:登陆请求的参数
 * @author:郑鹏宇
 * @date: 2018/4/12
 */
@ApiModel(value="登录校验条件",description="登录Query")
public class LoginQuery {
    /**
     * 电话
     */
    @ApiModelProperty(value="电话",example="13545139270")
    private String phone;
    /**
     * 短信验证码
     */
    @ApiModelProperty(value="验证码",example="123456")
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
