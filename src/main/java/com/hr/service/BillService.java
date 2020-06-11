package com.hr.service;

import com.alibaba.fastjson.JSONObject;
import com.hr.model.TaskModel;

import java.util.Map;

public interface BillService {

    JSONObject list();

    JSONObject DeptList(String code);

    JSONObject taskList(Map<String, String> map);

    JSONObject Add(TaskModel taskModel);
}
