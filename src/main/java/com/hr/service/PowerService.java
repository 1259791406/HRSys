package com.hr.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface PowerService {
    JSONObject list();

    JSONObject GetOne(String id);

    JSONObject UpdateRole(Map<String, Object> map);

    JSONObject GetList();

    JSONObject AddData(Map<String, Object> map);

    JSONObject RoleList();
}