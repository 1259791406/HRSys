package com.hr.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hr.Util.Util;
import com.hr.dao.WorkbenchDao;
import com.hr.model.CalendarList;
import com.hr.model.Workbench;
import com.hr.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class WorkImpl implements WorkService {

    @Autowired
    private WorkbenchDao workbenchDao;

    @Override
    public JSONObject list(Workbench workbench) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        if (workbench.getUsername() == "") {
            workbench.setUsername(null);
        }
        List<Workbench> lists = workbenchDao.list(workbench);
        int page = workbench.getPage();
        int Limit = workbench.getLimit();
        for (Workbench workbench1 : lists) {
            workbench1.setPage(page);
            workbench1.setLimit(Limit);
            array.add(JSON.parseObject(JSON.toJSONString(workbench1)));
        }
        json.put("code", 0);
        json.put("data", array);
        json.put("mes", "工作台信息查询成功！");
        json.put("count", workbenchDao.count(workbench));
        return json;
    }

    @Override
    public JSONObject add(Workbench workbench) {
        JSONObject json = new JSONObject();
        workbench.setApp("test");
        workbench.setCode(Util.GetUUID());
        if (workbench.getzGoTime().equals("")) {
            workbench.setzGoTime(null);
        }
        if (workbench.getzOutTime().equals("")) {
            workbench.setzOutTime(null);
        }
        workbenchDao.add(workbench);
        json.put("code", 200);
        json.put("mes", "排班记录增加成功！");
        return json;
    }

    @Override
    public JSONObject update(Workbench workbench) {
        JSONObject json = new JSONObject();
        workbenchDao.update(workbench);
        json.put("code", 200);
        json.put("mes", "修改成功！");
        return json;
    }
}
