package com.jiuzhe.hotel.module;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description:用户的数据Dto
 * @author:郑鹏宇
 * @date: 2018/3/28
 */
@ApiModel(value="用户修改请求",description="用户的修改请求")
public class UserQuery {

    @ApiModelProperty(value="属性ID",example="1",required = true)
    private String id;

    @ApiModelProperty(value="用户昵称",example="zpy")
    private String userName;

    @ApiModelProperty(value="用户头像",example="https://dev-hotel.oss-cn-hangzhou.aliyuncs.com/hotel_items/001.gif?x-oss-process=style/test")
    private String imgUrl;

    @ApiModelProperty(value="用户性别",example="男")
    private String sex;

    @ApiModelProperty(value="用户生日",example="2018-03-14")
    private String birthday;

    @ApiModelProperty(value="用户邮箱",example="135451892@qq.com")
    private String email;

    @ApiModelProperty(value="用户真实姓名",example="杨虎")
    private String realName;

    @ApiModelProperty(value="身份证",example="420582199603681931")
    private String idCard;

    @ApiModelProperty(value="备注",example="随意玩")
    private String remark;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
