package com.jiuzhe.hotel.dao.mapper;

import com.jiuzhe.hotel.entity.User;
import com.jiuzhe.hotel.utils.StringUtil;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Description:用户管理的sql
 * @author:郑鹏宇
 * @date: 2018/3/28
 */
public class UserProvider {
    /**
     * @Description:根据前台id获取用户信息
     * @author:郑鹏宇
     * @date:2018/3/28
     */
    public String getUserById(String id) {
        return new SQL() {{
            SELECT("id,user_name userName," +
                    "phone,email,real_name realName,id_card idCard," +
                    "remark,img_url imgUrl,sex,birthday");
            FROM("jzt_user");
            if (!StringUtil.isEmptyOrNull(id)) {
                WHERE("id=#{id}");
            }
        }}.toString();
    }

    /**
     * @Description:根据前台phone获取用户信息
     * @author:郑鹏宇
     * @date:2018/4/24
     */
    public String getUserByPhone(String phone) {
        return new SQL() {{
            SELECT("id,user_name userName," +
                    "phone,email,real_name realName,id_card idCard," +
                    "remark,img_url imgUrl,sex,birthday");
            FROM("jzt_user");
            if (!StringUtil.isEmptyOrNull(phone)) {
                WHERE("phone=#{phone}");
            }
        }}.toString();
    }

    public String getUser(User user) {
        return new SQL() {{
            SELECT("id,user_name userName,password," +
                    "phone,email,real_name realName,id_card idCard," +
                    "remark,img_url imgUrl,sex,birthday");
            FROM("jzt_user");
            if (!StringUtil.isEmptyOrNull(user.getId())) {
                WHERE("id=#{id}");
            }
            if (!StringUtil.isEmptyOrNull(user.getPhone())) {
                WHERE("phone=#{phone}");
            }
        }}.toString();
    }

    /**
     * @Description:修改除手机以外的所有属性
     * @author:郑鹏宇
     * @date:2018/3/28
     */
    public String updateUser(User user) {
        return new SQL() {{
            UPDATE("jzt_user");
            if (!StringUtil.isEmptyOrNull(user.getUserName())) {
                SET("user_name=#{userName}");
            }
            if (!StringUtil.isEmptyOrNull(user.getPhone())) {
                SET("phone=#{phone}");
            }
            if (!StringUtil.isEmptyOrNull(user.getEmail())) {
                SET("email=#{email}");
            }
            if (!StringUtil.isEmptyOrNull(user.getRealName())) {
                SET("real_name=#{realName}");
            }
            if (!StringUtil.isEmptyOrNull(user.getIdCard())) {
                SET("id_card=#{idCard}");
            }
            if (!StringUtil.isEmptyOrNull(user.getRemark())) {
                SET("remark=#{remark}");
            }
            if (!StringUtil.isEmptyOrNull(user.getImgUrl())) {
                SET("img_url=#{imgUrl}");
            }
            if (!StringUtil.isEmptyOrNull(user.getSex())) {
                SET("sex=#{sex}");
            }
            if (!StringUtil.isEmptyOrNull(user.getBirthday())) {
                SET("birthday=#{birthday}");
            }
            WHERE("id=#{id}");
        }}.toString();
    }

    /**
     * @Description:校验电话号码是否为存在
     * @author:郑鹏宇
     * @date:2018/3/30
     */
    public String checkPhone(User user) {
        return new SQL() {{
            SELECT("count(1)");
            FROM("jzt_user");
            if (!StringUtil.isEmptyOrNull(user.getId())) {
                WHERE("id=#{id}");
            }
            if (!StringUtil.isEmptyOrNull(user.getPhone())) {
                WHERE("phone=#{phone}");
            }
        }}.toString();
    }

    /**
     * @Description:注册
     * @author:郑鹏宇
     * @date:2018/4/2
     */
    public String saveUser(User user) {
        return new SQL() {{
            INSERT_INTO("jzt_user");
            if (!StringUtil.isEmptyOrNull(user.getId())) {
                VALUES("id", "#{id}");
            }
            if (!StringUtil.isEmptyOrNull(user.getPhone())) {
                VALUES("phone", "#{phone}");
            }
        }}.toString();
    }
}
