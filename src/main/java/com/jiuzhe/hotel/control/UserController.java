package com.jiuzhe.hotel.control;

import com.jiuzhe.hotel.constants.CommonConstant;
import com.jiuzhe.hotel.dto.ResponseBase;
import com.jiuzhe.hotel.dto.UserDto;
import com.jiuzhe.hotel.module.PhoneCheckQuery;
import com.jiuzhe.hotel.module.UserQuery;
import com.jiuzhe.hotel.service.UserService;
import com.jiuzhe.hotel.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:用户管理的controller
 * @author:郑鹏宇
 * @date: 2018/3/28
 */
@Api(tags = "用户User的controller")
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    /**
     * @Description:根据id获取用户信息
     * @author:郑鹏宇
     * @date:2018/3/28
     */
    @ApiOperation(value = "根据id获取用户信息", notes = "输入id")
    @GetMapping
    public ResponseBase<UserDto> getUserById(@ApiParam(name = "id", value = "id") @RequestParam(required = true) String id) {
        ResponseBase<UserDto> responseBase = new ResponseBase();
        try {
            UserDto userDto = userService.getUserById(id);
            responseBase.setStatus(CommonConstant.SUCCESS);
            responseBase.setData(userDto);
        } catch (Exception e) {
            logger.error("get user message by id failed", e);
            logger.debug("根据id获取用户信息错误 : " + id);
            responseBase.setStatus(CommonConstant.FAIL);
            responseBase.setMessage(e.getMessage());
        }
        return responseBase;
    }

    @ApiOperation(value = "根据phone获取用户信息", notes = "输入电话")
    @GetMapping("/phone")
    public ResponseBase<UserDto> getUserByPhone(@ApiParam(name = "phone", value = "phone") @RequestParam(required = true) String phone) {
        ResponseBase<UserDto> responseBase = new ResponseBase();
        try {
            //需要先判定对方的电话是不是九折科技已经注册的电话
            UserDto userDto = userService.getUserByPhone(phone);
            if (userDto != null) {
                responseBase.setStatus(CommonConstant.SUCCESS);
                responseBase.setData(userDto);
            } else {
                responseBase.setStatus(CommonConstant.FAIL);
                responseBase.setMessage("没这个账号");
            }

        } catch (Exception e) {
            logger.error("get user message by phone failed", e);
            logger.debug("根据phone获取用户信息错误 : " + phone);
            responseBase.setStatus(CommonConstant.FAIL);
            responseBase.setMessage(e.getMessage());
        }
        return responseBase;
    }

    /**
     * @Description:修改我的信息（不能修改电话号码）
     * @author:郑鹏宇
     * @date:2018/4/12
     */
    @ApiOperation(value = "修改个人信息", notes = "id必须有，电话号码无法修改")
    @PostMapping("/upuser")
    public ResponseBase updateUser(@ApiParam(name = "UserQuery", value = "userQuery") @RequestBody UserQuery userQuery) {
        ResponseBase responseBase = new ResponseBase<>();
        if (null != userQuery && !StringUtil.isEmptyOrNull(userQuery.getId())) {
            try {
                userService.updateUser(userQuery);
                responseBase.setStatus(CommonConstant.SUCCESS);
                responseBase.setMessage("成功了");
            } catch (Exception e) {
                logger.error("update user message failed", e);
                logger.debug("修改用户信息错误！");
                responseBase.setStatus(CommonConstant.FAIL);
                responseBase.setMessage("异常");
            }
        } else {
            responseBase.setStatus(CommonConstant.FAIL);
            responseBase.setMessage("没有对象或者对象缺id");
        }
        return responseBase;
    }

    /**
     * @Description:检验原手机号
     * @author:郑鹏宇
     * @date:2018/4/13
     */
    @ApiOperation(value = "检验原手机号", notes = "需要id，phone,messageCode")
    @PostMapping("/oldphone")
    public ResponseBase<Map<String, String>> checkOldPhone(
            @ApiParam(name = "query", value = "query") @RequestBody PhoneCheckQuery query) {
        ResponseBase<Map<String, String>> responseBase = new ResponseBase();
        try {
            Integer check = userService.checkOldPhone(query);
            responseBase.setStatus(check);
            responseBase.setData(new HashMap<String, String>() {{
                put("id", query.getId());
            }});
        } catch (Exception e) {
            logger.error("check old phone failed", e);
            responseBase.setStatus(CommonConstant.FAIL);
            responseBase.setMessage("异常");
        }
        return responseBase;
    }

    /**
     * @Description:检验新手机号
     * @author:郑鹏宇
     * @date:2018/4/13
     */
    @ApiOperation(value = "检验新手机号", notes = "需要id，phone,messageCode")
    @PostMapping("/newphone")
    public ResponseBase<Map<String, String>> checkNewPhone(
            @ApiParam(name = "query", value = "query") @RequestBody PhoneCheckQuery query) {
        ResponseBase<Map<String, String>> responseBase = new ResponseBase();
        try {
            Integer check = userService.checkNewPhone(query);
            responseBase.setStatus(check);
            responseBase.setData(new HashMap<String, String>() {{
                put("id", query.getId());
            }});
        } catch (Exception e) {
            logger.error("check new phone failed", e);
            responseBase.setStatus(CommonConstant.FAIL);
            responseBase.setMessage("异常");
        }
        return responseBase;
    }

    /**
     * @Description:修改新电话号码
     * @author:郑鹏宇
     * @date:2018/4/13
     */
    @ApiOperation(value = "修改成新手机", notes = "需要id")
    @PostMapping("/upnewphone")
    public ResponseBase upNewPhone(
            @ApiParam(name = "map", value = "map") @RequestBody Map<String, String> map) {
        ResponseBase responseBase = new ResponseBase();
        String id = map.get("id").toString();
        try {
            Boolean check = userService.upNewPhone(id);
            if (check) {
                responseBase.setStatus(CommonConstant.SUCCESS);
                responseBase.setMessage("新电话修改成功");
            } else {
                responseBase.setStatus(CommonConstant.NEWPHONE_FAIL);
                responseBase.setMessage("新电话修改失败（可能是redis过期）");
            }
        } catch (Exception e) {
            logger.error("upnewphone failed", e);
            logger.debug("修改新电话号码异常！");
            responseBase.setStatus(CommonConstant.FAIL);
            responseBase.setMessage("修改新电话号码异常！");
        }
        return responseBase;
    }

}
