package com.jiuzhe.hotel.control;

import com.jiuzhe.hotel.constants.CommonConstant;
import com.jiuzhe.hotel.dto.RecommentDto;
import com.jiuzhe.hotel.dto.ResponseBase;
import com.jiuzhe.hotel.dto.SearchQueryDto;
import com.jiuzhe.hotel.dto.SkuDetailDto;
import com.jiuzhe.hotel.entity.HotelSku;
import com.jiuzhe.hotel.entity.Search;
import com.jiuzhe.hotel.entity.SkuDetail;
import com.jiuzhe.hotel.module.RecommetQuery;
import com.jiuzhe.hotel.module.SearchQuery;
import com.jiuzhe.hotel.module.SkuDetailQuery;
import com.jiuzhe.hotel.service.HotelorderService;
import com.jiuzhe.hotel.service.SkuSearchService;
import com.jiuzhe.hotel.utils.CheckUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description:酒店信息推送的controller
 * @author:郑鹏宇
 * @date:2018/3/20
 */
@Api(tags = "首页搜索界面的酒店展示的controller")
@RestController
@RequestMapping("/skusearch")
public class SkuSeachController {
    private static final Logger logger = LoggerFactory.getLogger(SkuSeachController.class);

    @Autowired
    SkuSearchService skuSearchService;
    @Autowired
    HotelorderService hotelorderService;

    /**
     * @Description:从数据库取数据（选择日期价格区间显示酒店状态价格）
     * @author:郑鹏宇
     * @date:2018/4/7
     */
    @ApiOperation(value = "选择日期价格区间显示酒店状态价格", notes = "需要日期区间，价格区间")
    @RequestMapping(method = RequestMethod.POST, value = "/hotels")
    public ResponseBase<List<SearchQueryDto>> getHotelItems(@RequestBody() SearchQuery searchQuery) {
        ResponseBase<List<SearchQueryDto>> responseBase = new ResponseBase<>();
        try {
            List<Search> searchs = skuSearchService.getHotelItems(searchQuery);
            List<SearchQueryDto> dtos = searchs.stream()
                    .map(search -> SearchQueryDto.make(search))
                    .collect(Collectors.toList());
            responseBase.setStatus(CommonConstant.SUCCESS);
            responseBase.setData(dtos);
        } catch (Exception e) {
            logger.error("getHotelItems failed", e);
            responseBase.setStatus(CommonConstant.FAIL);
        }
        return responseBase;

    }

    /**
     * @Description:点击地图图标展示预定酒店详情
     * @author:郑鹏宇
     * @date:2018/4/9
     */
    @ApiOperation(value = "查询酒店详情", notes = "点击地图弹出酒店详情使用")
    @PostMapping("/detail")
    public ResponseBase<SkuDetailDto> getSkuFacilitys(@RequestBody SkuDetailQuery skuDetailQuery) {
        ResponseBase<SkuDetailDto> responseBase = new ResponseBase<>();
        if (!CheckUtil.compare_date(skuDetailQuery.getStartDate(), skuDetailQuery.getEndDate())) {
            responseBase.setStatus(CommonConstant.QUERY);
            responseBase.setMessage("日期区间有问题");
            return responseBase;
        }
        try {
            SkuDetailDto skuDetailDto = skuSearchService.getSkuFacilitys(skuDetailQuery);
            responseBase.setStatus(CommonConstant.SUCCESS);
            responseBase.setData(skuDetailDto);
        } catch (Exception e) {
            logger.error("dian ji di tu tu biao zan shi chu wen ti!", e);
            responseBase.setStatus(CommonConstant.FAIL);
            responseBase.setMessage("异常");
        }
        return responseBase;
    }

    /**
     * @Description:通过skuId获取房间的所有信息
     * @author:郑鹏宇
     * @date:2018/6/8
     */
    @PostMapping("/skudetail")
    public ResponseBase<SkuDetailDto> getSkuDetailBySkuId(@RequestBody Map<String, String> skuIdMap) {
        ResponseBase<SkuDetailDto> responseBase = new ResponseBase<>();
        String skuId = skuIdMap.get("skuId");
        try {
            SkuDetail skuDetail = skuSearchService.getSkuDetailBySkuId(skuId);
            SkuDetailDto dto = SkuDetailDto.make(skuDetail);
            responseBase.setStatus(CommonConstant.SUCCESS);
            responseBase.setData(dto);
        } catch (Exception e) {
            responseBase.setStatus(CommonConstant.FAIL);
            logger.error("getSkuDetailBySkuId failed!", e);
        }
        return responseBase;
    }

    /**
     * @Description:根据地域，评分来帅选房间(标准是weight)
     * @author:郑鹏宇
     * @date:2018/4/9
     */
    @ApiOperation(value = "根据地域，评分来筛选房间", notes = "根据地域，评分来帅选房间")
    @PostMapping("/recommend")
    public ResponseBase<List<RecommentDto>> getRecommendHotel(@RequestBody RecommetQuery query) {
        ResponseBase<List<RecommentDto>> responseBase = new ResponseBase<>();
        try {
            List<HotelSku> hotelSkus = skuSearchService.getRecommendHotel(query);
            List<RecommentDto> dtos = hotelSkus.stream()
                    .map(hotelSku -> RecommentDto.make(hotelSku))
                    .collect(Collectors.toList());
            responseBase.setStatus(CommonConstant.SUCCESS);
            responseBase.setData(dtos);
        } catch (Exception e) {
            responseBase.setStatus(CommonConstant.FAIL);
            logger.error("recommend failed!", e);
        }
        return responseBase;
    }

    /**
     * @Description: 根据房号查询门店电话
     * @Author: luan
     * @Date: 2018/11/7/007
     */
    @GetMapping("/phone/{skuId}")
    public ResponseBase<String> getPhoneBySkuId(@PathVariable String skuId) {

        ResponseBase<String> responseBase = new ResponseBase<>();
        try {
            String phone = skuSearchService.getPhoneBySkuId(skuId);
            responseBase.setStatus(CommonConstant.SUCCESS);
            if (null == phone) responseBase.setMessage("该门店没有预留电话！");
            responseBase.setData(phone);

        } catch (Exception e) {
            responseBase.setStatus(CommonConstant.FAIL);
            logger.error("查询门店电话异常！", e);
        }
        return responseBase;
    }

    @PostMapping("/merchantPhone")
    public ResponseBase getMerchantPhone(@RequestBody Map map) {
        String id = map.get("merchantId").toString();
        ResponseBase responseBase = new ResponseBase<>();
        try {
            String phone = skuSearchService.getMerchantPhone(id);
            Map resulyMap = new HashMap();
            resulyMap.put("phone", phone);
            responseBase.setStatus(CommonConstant.SUCCESS);
            responseBase.setData(resulyMap);
            return responseBase;
        } catch (Exception e) {
            responseBase.setStatus(CommonConstant.FAIL);
            logger.error("查询门店电话异常！", e);
        }
        return responseBase;
    }

    @PostMapping("/roomstatus")
    public ResponseBase getRoomstatus(@RequestBody Map map) {
        String id = map.get("id").toString();
        String startDate = map.get("startDate").toString();
        String endDate = map.get("endDate").toString();
        ResponseBase responseBase = new ResponseBase<>();
        try {
            Map resultMap = hotelorderService.getReservation(id, startDate, endDate);
            if ("0".equals(resultMap.get("status"))) {
                List data = (List) resultMap.get("data");
                String canBeReserved = data.get(1).toString();
                if (!"1".equals(canBeReserved)) {
                    responseBase.setStatus(CommonConstant.RESERVER);
                    responseBase.setMessage("该房间已经被预定");
                    return responseBase;
                }
            } else {
                throw new RuntimeException();
            }
            responseBase.setStatus(CommonConstant.SUCCESS);
            return responseBase;
        } catch (Exception e) {
            responseBase.setStatus(CommonConstant.FAIL);
            logger.error("查询店铺入住状态异常！", e);
        }
        return responseBase;
    }


}
