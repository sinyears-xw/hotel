package com.jiuzhe.hotel.entity;

/**
 * @Description:
 * @author:郑鹏宇
 * 酒店评价的Entity
 * @date 2018/10/23/023
 */
public class Evaluate {
    //
    private String skuProblem;
    private String cleanProblem;
    private Integer skuScore;
    private Integer cleanScore;

    public String getSkuProblem() {
        return skuProblem;
    }

    public void setSkuProblem(String skuProblem) {
        this.skuProblem = skuProblem;
    }

    public String getCleanProblem() {
        return cleanProblem;
    }

    public void setCleanProblem(String cleanProblem) {
        this.cleanProblem = cleanProblem;
    }

    public Integer getSkuScore() {
        return skuScore;
    }

    public void setSkuScore(Integer skuScore) {
        this.skuScore = skuScore;
    }

    public Integer getCleanScore() {
        return cleanScore;
    }

    public void setCleanScore(Integer cleanScore) {
        this.cleanScore = cleanScore;
    }
}
