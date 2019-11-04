package com.jiuzhe.hotel.dto;

/**
 * @Description:
 * @author:郑鹏宇
 * @date 2018/8/7/007
 */
public class TakenDto {
    private String hrId;

    private String taken;

    private Integer hrType;


    public String getHrId() {
        return hrId;
    }

    public void setHrId(String hrId) {
        this.hrId = hrId;
    }

    public String getTaken() {
        return taken;
    }

    public void setTaken(String taken) {
        this.taken = taken;
    }

    public Integer getHrType() {
        return hrType;
    }

    public void setHrType(Integer hrType) {
        this.hrType = hrType;
    }
}
