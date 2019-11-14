package com.jiuzhe.hotel.service.impl;

import com.jiuzhe.hotel.constants.CommonConstant;
import com.jiuzhe.hotel.dao.UserDao;
import com.jiuzhe.hotel.dto.UserDto;
import com.jiuzhe.hotel.entity.User;
import com.jiuzhe.hotel.module.PhoneCheckQuery;
import com.jiuzhe.hotel.module.UserQuery;
import com.jiuzhe.hotel.service.UserService;
import com.jiuzhe.hotel.service.aliyun.AcsService;
import com.jiuzhe.hotel.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Description:用户的imlp
 * @author:郑鹏宇
 * @date: 2018/3/28
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    @Resource
    UserDao userDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    AcsService service;


    /**
     * @Description:检验是否是原电话号码，是：返回ture并发送验证码，不是：返回false
     * @author:郑鹏宇
     * @date:2018/4/13
     */
    @Override
    public Integer checkOldPhone(PhoneCheckQuery query) {
        User user = new User();
        user.setId(query.getId());
        user.setPhone(query.getPhone());
        if (userDao.checkPhone(user) != 1) {
            return CommonConstant.OLDPHONE_FAIL;
        }
        //todo 发送验证码
        Integer rst = service.sendValidateCode(query.getPhone(), query.getMessageCode(), query.getId());

        return rst;
    }

    /**
     * @Description:检验是否是新电话号码。是：true，发送短信并把电话存入redis，
     * @author:郑鹏宇
     * @date:2018/4/13
     */
    @Override
    public Integer checkNewPhone(PhoneCheckQuery query) {
        User user = new User();
        user.setId(query.getId());
        user.setPhone(query.getPhone());
        if (userDao.checkPhone(user) != 0) {
            return CommonConstant.NEWPHONE_FAIL;
        }
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set("plantform:user:change:new-phone:" + query.getId(), query.getPhone(), 180, TimeUnit.SECONDS);
        Integer rst = service.sendValidateCode(query.getPhone(), query.getMessageCode(), query.getId());
        return rst;
    }

    /**
     * @Description:修改新电话号码
     * @author:郑鹏宇
     * @date:2018/4/13
     */
    @Override
    public Boolean upNewPhone(String id) {
        Boolean check = false;
        User user = new User();
        BoundValueOperations<String, String> redisMap = redisTemplate.boundValueOps("plantform:user:change:new-phone:" + id);
        String newPhone = redisMap.get();
        user.setId(id);
        user.setPhone(newPhone);
        if (userDao.updateUser(user) == 1) {
            check = true;
        }
        return check;
    }

    /**
     * @Description:修改电话号码
     * @author:郑鹏宇
     * @date:2018/4/13
     */
    @Override
    public int updateUserPhone(String id, String phone) {
        User user = new User();
        if (!StringUtil.isEmptyOrNull(id)) {
            user.setId(id);
        }
        if (!StringUtil.isEmptyOrNull(phone)) {
            user.setPhone(phone);
        }
        return userDao.updateUser(user);
    }

    /**
     * @Description:通过id获取用户信息
     * @author:郑鹏宇
     * @date:2018/4/13
     */
    @Override
    public UserDto getUserById(String id) {
        return userDao.getUserById(id);
    }

    /**
     * @Description:通过phone获取用户信息
     * @author:郑鹏宇
     * @date:2018/4/24
     */
    @Override
    public UserDto getUserByPhone(String phone) {
        return userDao.getUserByPhone(phone);
    }

    /**
     * @Description:修改用户信息
     * @author:郑鹏宇
     * @date:2018/4/12
     */
    @Override
    public int updateUser(UserQuery userQuery) {
        User user = new User();
        user.setId(userQuery.getId());
        user.setEmail(userQuery.getEmail());
        user.setBirthday(userQuery.getBirthday());
        user.setIdCard(userQuery.getIdCard());
        user.setImgUrl(userQuery.getImgUrl());
        user.setRealName(userQuery.getRealName());
        user.setSex(userQuery.getSex());
        user.setRemark(userQuery.getRemark());
        user.setUserName(userQuery.getUserName());
        return userDao.updateUser(user);
    }
}
