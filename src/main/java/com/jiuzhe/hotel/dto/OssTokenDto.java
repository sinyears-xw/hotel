package com.jiuzhe.hotel.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @name: OssTokenDto
 * @author: lucifinier
 * @date: 2018/4/27 09:37
 * @description: OSS临时token
 */
@ApiModel(value="Oss Token",description="Oss Token模型DTO")
public class OssTokenDto implements Serializable {
    private static final long serialVersionUID = -5904071073338448854L;

    @ApiModelProperty(value="AccessKeyId",example="87d9fe4238f74d84be15d62984fdfe65")
    private String accessKeyId;
    @ApiModelProperty(value="AccessKeySecret",example="87d9fe4238f74d84be15d62984fdfe65")
    private String accessKeySecret;
    @ApiModelProperty(value="securityToken",example="87d9fe4238f74d84be15d62984fdfe65")
    private String securityToken;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }
}
