package com.jiuzhe.hotel.control;

import com.jiuzhe.hotel.constants.CommonConstant;
import com.jiuzhe.hotel.dto.ResponseBase;
import com.jiuzhe.hotel.service.AdvertisementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @name: AdvertisementController
 * @author: lucifinier
 * @date: 2018/5/4 18:29
 * @description: TODO
 */
@Api(tags = "广告的controller")
@RestController
@RequestMapping("/advertisements")
public class AdvertisementController {
    @Autowired
    AdvertisementService advertisementService;

    @GetMapping
    @ApiOperation("广告的查询")
    public ResponseBase<List<String>> saveUserFeedback() {
        ResponseBase<List<String>> res = new ResponseBase<>();
        try {
            List<String> adImgUrls = advertisementService.getAllAdImgUrl();
            res.setStatus(CommonConstant.SUCCESS);
            res.setData(adImgUrls);
        } catch (Exception e) {
            res.setStatus(CommonConstant.FAIL);
            res.setMessage("获取广告图片地址失败");
        }

        return res;
    }
}
