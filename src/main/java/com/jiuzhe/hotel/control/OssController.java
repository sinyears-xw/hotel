package com.jiuzhe.hotel.control;

import com.jiuzhe.hotel.service.SkuSearchService;
import com.jiuzhe.hotel.service.aliyun.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:
 * @author:郑鹏宇
 * @date 2018/8/15/015
 */

@RestController
@RequestMapping("/oss")
public class OssController {
    @Autowired
    OssService ossService;
    @Autowired
    SkuSearchService skuSearchService;

    /**
     * @Description:删除阿里云上多余的图片
     * @author: 郑鹏宇
     * @date 2018/8/16/016
     */
    @GetMapping
    public void deletImgs() {
        //数据库的图
        List<String> DbImg = skuSearchService.getAllimgsFromDb();
        //阿里云的图
        List<String> AliImg = ossService.getAllHotelImg();
        AliImg.removeAll(DbImg);
        //删除多余的
        ossService.deleteHotelImg(AliImg);
    }
}
