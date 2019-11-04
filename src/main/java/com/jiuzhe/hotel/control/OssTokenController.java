package com.jiuzhe.hotel.control;

import com.jiuzhe.hotel.constants.CommonConstant;
import com.jiuzhe.hotel.dto.OssTokenDto;
import com.jiuzhe.hotel.dto.ResponseBase;
import com.jiuzhe.hotel.service.aliyun.OssToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @name: OssTokenController
 * @author: lucifinier
 * @date: 2018/4/27 11:16
 * @description: OSS临时Token
 */
@Api(tags = "获取临时令牌的controller")
@RestController
@RequestMapping("/osstoken")
public class OssTokenController {
    private static final Logger logger = LoggerFactory.getLogger(OssTokenController.class);

    private final String CACHE_OSS_TOKEN_PREFIX = "platform:oss:access:token:usr-id:";

    @Autowired
    OssToken ossToken;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping
    @ApiOperation(value = "获取临时令牌", notes = "获取临时令牌")
    public ResponseBase<OssTokenDto> getOssAccessToken(
            @ApiParam(name = "id", value = "用户id") @RequestParam String id
    ) {
        ResponseBase<OssTokenDto> res = new ResponseBase<>();

        ValueOperations<String, OssTokenDto> operations = redisTemplate.opsForValue();

        try {
            OssTokenDto dto = operations.get(CACHE_OSS_TOKEN_PREFIX + id);
            if (null == dto) {
                dto = ossToken.getOssToken();
                operations.set(CACHE_OSS_TOKEN_PREFIX + id, dto, 60, TimeUnit.SECONDS);
            }

            res.setStatus(CommonConstant.SUCCESS);
            res.setData(dto);
        } catch (Exception e) {
            logger.error("get oss token faild.", e);

            res.setStatus(CommonConstant.FAIL);
            res.setMessage("获取临时令牌失败");
        }

        return res;
    }
}
