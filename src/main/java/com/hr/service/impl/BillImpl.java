package com.hr.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hr.Overall.UtilModel;
import com.hr.Util.Util;
import com.hr.dao.BillDao;
import com.hr.dao.TaskDao;
import com.hr.model.Task;
import com.hr.model.TaskModel;
import com.hr.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class BillImpl implements BillService {

    @Autowired
    private BillDao billDao;

    @Autowired
    private TaskDao taskDao;

    @Override
    public JSONObject list() {
        JSONObject json = new JSONObject();
        json.put("code", 200);
        json.put("data", JSONArray.parseArray(JSON.toJSONString(billDao.list())));
        return json;
    }

    @Override
    public JSONObject DeptList(String code) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        List<UtilModel> list = billDao.DeptList(code);
        for (int i = 0; i < list.size(); i++) {
            array.add(list.get(i));
        }
        json.put("code", 200);
        json.put("data", array);
        json.put("mes", "获取成功！");
        return json;
    }

    @Override
    public JSONObject taskList(Map<String, String> map) {
        JSONObject json = new JSONObject();
        json.put("code", 200);
        json.put("data", JSONArray.parseArray(JSON.toJSONString(billDao.taskList(map))));
        json.put("mes", "获取成功！");
        return json;
    }

    @Override
    public JSONObject Add(TaskModel taskModel) {
        JSONObject json = new JSONObject();
        String dept = taskModel.getDept();
        String table = taskModel.getType();
        String code = billDao.GetCode(taskModel);
        if (code == null || code == "") {
            code = Util.GetUUID();
        }else {
            billDao.Delete(code);
        }
        List<Task> list = taskModel.getList();
        for (int i = 0; i < list.size(); i++) {
            Task task = new Task();
            task.setUserid(list.get(i).getUserid());
            task.setCode(code);
            task.setState(1);
            task.setOrderNum(i + 1);
            billDao.Add(task);
        }
        json.put("code", 200);
        json.put("mes", "添加成功！");
        return json;
    }
}