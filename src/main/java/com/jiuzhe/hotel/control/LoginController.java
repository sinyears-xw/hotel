package com.jiuzhe.hotel.control;

import com.jiuzhe.hotel.constants.CommonConstant;
import com.jiuzhe.hotel.constants.SendMessageEnum;
import com.jiuzhe.hotel.dto.ResponseBase;
import com.jiuzhe.hotel.dto.TakenkDto;
import com.jiuzhe.hotel.dto.UserDto;
import com.jiuzhe.hotel.module.LoginQuery;
import com.jiuzhe.hotel.service.LoginService;
import com.jiuzhe.hotel.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @Description:
 * @author:郑鹏宇
 * @date: 2018/4/11
 */
@Api(tags = "用户登录的controller")
@RestController
@RequestMapping("/login")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    /**
     * @Description:登陆注册输入电话号码，校验合法性，注册，发送验证码
     * @author:郑鹏宇
     * @date:2018/4/11
     */
    @ApiOperation(value = "登录注册", notes = "输入phone,messageCode")
    @PostMapping("/phone")
    public ResponseBase<Map<String, String>> checkLoginPhone(
            @ApiParam(name = "loginQuery", value = "登陆所需要的参数") @RequestBody LoginQuery loginQuery) {
        ResponseBase<Map<String, String>> responseBase = new ResponseBase();
        try {
            Map<Integer, String> result = loginService.checkLoginPhone(loginQuery);
            Map<String, String> map = new HashMap<>();
            Integer status = null;
            Set<Integer> set = result.keySet();
            for (Integer key : set) {
                map.put("id", result.get(key));
                status = key;
            }
            responseBase.setStatus(status);
            responseBase.setData(map);
        } catch (Exception e) {
            logger.error("logion phone failed", e);
            responseBase.setStatus(SendMessageEnum.FAILED.getIndex());
            responseBase.setMessage("异常");
        }
        return responseBase;
    }

    /**
     * @Description:短信校验（takening）
     * @author:郑鹏宇
     * @date:2018/4/11
     */
    @ApiOperation(value = "生成taken", notes = "输入id")
    @PostMapping
    public ResponseBase<TakenkDto> checkMsg(
            @ApiParam(name = "Map", defaultValue = "1") @RequestBody Map<String, String> map) {
        ResponseBase<TakenkDto> responseBase = new ResponseBase<>();
        String id = map.get("id");
        try {
            TakenkDto msgCheckDto = loginService.saveTaken(id);
            UserDto userDto = userService.getUserById(id);
            msgCheckDto.setUserDto(userDto);
            responseBase.setStatus(CommonConstant.SUCCESS);
            responseBase.setData(msgCheckDto);
        } catch (Exception e) {
            logger.error("taken failed", e);
            logger.debug("登陆保存taken有问题");
            responseBase.setStatus(CommonConstant.FAIL);
            responseBase.setMessage("异常");
        }
        return responseBase;
    }

    @ApiOperation(value = "自动登陆", notes = "根据用户id判断是否可以自动登陆")
    @PostMapping(value = "/autologin")
    public ResponseBase<TakenkDto> autoLogin(
            @ApiParam(name = "Map", defaultValue = "id:1") @RequestBody Map<String, String> map) {
        ResponseBase<TakenkDto> res = new ResponseBase<>();
        String id = map.get("id");
        try {
            TakenkDto msgCheckDto = loginService.autoLogin(id);

            //登陆有效则刷新token返回，不存在提示app重新登陆
            if (Optional.ofNullable(msgCheckDto).isPresent()) {
                res.setStatus(CommonConstant.SUCCESS);
                res.setData(msgCheckDto);
            } else {
                res.setStatus(CommonConstant.TOKEN_INVAIID);
                res.setMessage(CommonConstant.TOKEN_INVAIID_DESC);
            }

        } catch (Exception e) {
            logger.error("auto logion failea!", e);
            logger.debug("登陆保存taken有问题");
            res.setStatus(CommonConstant.FAIL);
            res.setMessage("异常");
        }
        return res;
    }
}
