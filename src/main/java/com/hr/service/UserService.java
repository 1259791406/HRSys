package com.hr.service;

import com.alibaba.fastjson.JSONObject;
import com.hr.model.User;

import java.text.ParseException;
import java.util.Map;

public interface UserService {
    /**
     * 登陆接口
     *
     * @param map phone：手机号   password：密码
     * @return 登陆人实体类
     */
    JSONObject Login(Map<String, Object> map);

    /**
     * 获取所有用户信息
     *
     * @param map
     * @return
     */
    JSONObject list(Map<String, Object> map);

    JSONObject VisList();

    JSONObject Add(User user);

    JSONObject GetOneUser(String userid);

    JSONObject UpdateData(User user) throws ParseException;

    JSONObject SelectUserCa(String orderid);

    JSONObject SelectUserList();

    JSONObject DeptUserList(Map<String, Object> map);

    JSONObject GetPass(Map<String, Object> map);

    //同步数据
    JSONObject synchro();

    JSONObject power(String userid);
}