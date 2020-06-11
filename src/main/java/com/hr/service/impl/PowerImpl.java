package com.hr.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hr.dao.JurisdictionDao;
import com.hr.dao.RoleDao;
import com.hr.model.Role;
import com.hr.service.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class PowerImpl implements PowerService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private JurisdictionDao jurisdictionDao;

    @Override
    public JSONObject list() {
        JSONObject json = new JSONObject();
        List<Role> list = roleDao.GetList();
        for (int i = 0; i < list.size(); i++) {
            String[] sign = list.get(i).getSign().split("-");
            String data = "";
            for (int a = 0; a < sign.length; a++) {
                data += (jurisdictionDao.GetData(sign[a]).getData() + "、");
            }
            list.get(i).setSign(data.substring(0, data.length() - 1));
        }
        json.put("code", 0);
        json.put("data", JSONArray.parseArray(JSON.toJSONString(list)));
        json.put("mes", "获取成功！");
        return json;
    }

    @Override
    public JSONObject GetOne(String id) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        Role role = roleDao.GetSign(id);
        String[] sign = role.getSign().split("-");
        for (int a = 0; a < sign.length; a++) {
            array.add(jurisdictionDao.GetData(sign[a]).getCode());
        }
        json.put("code", 200);
        json.put("mes", "成功！");
        json.put("sign", JSONArray.parseArray(JSON.toJSONString(jurisdictionDao.list())));
        json.put("data", array);
        json.put("role", role);
        return json;
    }

    @Override
    public JSONObject UpdateRole(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        String sign = map.get("sign").toString();
        sign = sign.substring(0, sign.length() - 1);
        map.put("sign", sign);
        roleDao.Update(map);
        json.put("code", 200);
        json.put("mes", "修改成功！");
        return json;
    }

    @Override
    public JSONObject GetList() {
        JSONObject json = new JSONObject();
        json.put("code", 200);
        json.put("data", JSONArray.parseArray(JSON.toJSONString(jurisdictionDao.list())));
        return json;
    }

    @Override
    public JSONObject AddData(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        String sign = map.get("sign").toString();
        sign = sign.substring(0, sign.length() - 1);
        map.put("sign", sign);
        roleDao.Add(map);
        json.put("code", 200);
        json.put("mes", "增加成功！");
        return json;
    }

    @Override
    public JSONObject RoleList() {
        JSONObject json = new JSONObject();

        return null;
    }
}