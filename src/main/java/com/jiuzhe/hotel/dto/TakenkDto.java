package com.jiuzhe.hotel.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description:短信校验返回的值
 * @author:郑鹏宇
 * @date: 2018/4/11
 */
@ApiModel(value="获取id和taken模型",description="获取id和taken模型")
public class TakenkDto {
    /**
     * 用户
     */
    @ApiModelProperty(value="属性ID",example="879cbd5001d9497fb7bfed660f98d7ce")
    private String id;
    /**
     *密匙
     */
    @ApiModelProperty(value="密匙taken",example="879cbd5001d9497fb7bfed660f98d7ce")
    private String taken;

    @ApiModelProperty(value="用户信息",example="user")
    private UserDto userDto;

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaken() {
        return taken;
    }

    public void setTaken(String taken) {
        this.taken = taken;
    }
}
