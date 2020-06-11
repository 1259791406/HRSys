package com.hr.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hr.dao.OvertimeDao;
import com.hr.dao.TaskListDao;
import com.hr.model.TaskList;
import com.hr.service.TaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class TaskListImpl implements TaskListService {

    @Autowired
    private TaskListDao taskListDao;

    @Autowired
    private OvertimeDao overtimeDao;

    @Override
    public JSONObject GetTableList(String code) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        List<TaskList> lists = taskListDao.GetTableList(code);
        for (TaskList taskList : lists) {
            String time = taskList.getTime();
            String opinion = taskList.getOpinion();
            if (time == "" || time == null) {
                taskList.setTime("");
            }
            if (opinion == "" || opinion == null) {
                taskList.setOpinion("");
            }
            array.add(taskList);
        }
        json.put("code", 200);
        json.put("data", array);
        return json;
    }
}