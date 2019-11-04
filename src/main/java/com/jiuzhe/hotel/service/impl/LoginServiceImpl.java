package com.jiuzhe.hotel.service.impl;

import com.jiuzhe.hotel.dao.LoginDao;
import com.jiuzhe.hotel.dto.TakenkDto;
import com.jiuzhe.hotel.entity.User;
import com.jiuzhe.hotel.module.LoginQuery;
import com.jiuzhe.hotel.service.LoginService;
import com.jiuzhe.hotel.service.aliyun.AcsService;
import com.jiuzhe.hotel.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @author:郑鹏宇
 * @date: 2018/4/11
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    LoginDao loginDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    AcsService service;

    @Value("${user.token.time-day}")
    String tokenTimeout;
    @Value("${user.msgcode.time-sec}")
    String msgcodeTimeout;

    /**
     * @Description:判定是否注册
     * @author:郑鹏宇
     * @date:2018/4/11
     */
    @Override
    public Map<Integer, String> checkLoginPhone(LoginQuery loginQuery) {
        String id;
        User user = loginDao.getUserByPhone(loginQuery.getPhone());
        if (null == user) {
            User user1 = new User();
            user1.setId(StringUtil.get32UUID());
            user1.setPhone(loginQuery.getPhone());
            loginDao.saveIdPhone(user1);
            id = user1.getId();
        } else {
            id = user.getId();
        }
        //TODO 发送短信验证码loginQuery.getMsgCode
        Integer rst = service.sendValidateCode(loginQuery.getPhone(), loginQuery.getMessageCode(), id);
        return new HashMap<Integer, String>(){{
            put(rst,id);
        }};
    }

    @Override
    public TakenkDto saveTaken(String id) {
        TakenkDto msgCheckDto = new TakenkDto();
        String taken = StringUtil.get32UUID();
        msgCheckDto.setId(id);
        msgCheckDto.setTaken(taken);
        //把taken放redis
        stringRedisTemplate.opsForValue().set("plantform:atuh:token:usr-id:" + msgCheckDto.getId(),
                taken, Long.parseLong(tokenTimeout), TimeUnit.DAYS);
        return msgCheckDto;
    }

    @Override
    public TakenkDto autoLogin(String id) {
        boolean hasToken = stringRedisTemplate.hasKey("plantform:atuh:token:usr-id:" + id);

        // 不存在token
        if (false == hasToken) {
            return null;
        }

        // 存在则刷新token，重新计时
        return this.saveTaken(id);
    }
}
