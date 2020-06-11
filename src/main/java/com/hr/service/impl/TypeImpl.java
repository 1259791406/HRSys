package com.hr.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hr.dao.TypeDao;
import com.hr.model.Type;
import com.hr.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class TypeImpl implements TypeService {

    @Autowired
    private TypeDao typeDao;

    /**
     * 获取所有类型
     *
     * @return
     */
    @Override
    public JSONObject list() {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        List<Type> list = typeDao.list();
        for (Type type : list) {
            array.add(JSON.parseObject(JSON.toJSONString(type)));
        }
        json.put("code", 200);
        json.put("data", array);
        json.put("count", typeDao.GetSize());
        json.put("mes", "数据获取成功！");
        return json;
    }
}