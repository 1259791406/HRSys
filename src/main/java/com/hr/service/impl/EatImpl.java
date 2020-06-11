package com.hr.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hr.Overall.UtilModel;
import com.hr.Util.Util;
import com.hr.dao.EatDao;
import com.hr.dao.TaskDao;
import com.hr.dao.TaskListDao;
import com.hr.model.*;
import com.hr.service.EatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EatImpl implements EatService {

    @Autowired
    private EatDao eatDao;

    @Autowired
    private TaskListDao taskListDao;

    @Autowired
    private TaskDao taskDao;

    @Override
    public JSONObject list(Eat eat) {
        JSONObject json = new JSONObject();
        if (eat.getUsername() == null || eat.getUsername() == "") {
            eat.setUsername(null);
        }
        if (eat.getOrigin() == null || eat.getOrigin() == "") {
            eat.setOrigin(null);
        }
        List<Eat> eatList = eatDao.ByEat(eat);
        json.put("code", 0);
        json.put("count", eatList.size());
        json.put("mes", "查询成功！");
        json.put("data", eatList);
        return json;
    }

    @Override
    public JSONObject Add(Eat eat) {
        JSONObject json = new JSONObject();
        String code = Util.GetCode();
        int userid = eat.getUserid();
        List<Task> list = taskDao.TaskList(new ToolModal(userid + "", "10007"));
        eat.setApp(list.get(0).getUserid() + "");
        eat.setCode(code);
        eatDao.addEat(eat);
        for (int i = 0; i < list.size(); i++) {
            TaskList taskList = new TaskList();
            taskList.setUserid(list.get(i).getUserid() + "");
            taskList.setType(list.get(i).getCode());
            taskList.setCode(code);
            taskList.setOrderNum(list.get(i).getOrderNum());
            taskListDao.AddData(taskList);
        }
        json.put("code", 200);
        json.put("mes", "增加成功！");
        return json;
    }

    @Override
    public JSONObject App(Approval approval) {
        JSONObject json = new JSONObject();
        String code = approval.getCode();
        String app = eatDao.GetApp(code);
        String state = approval.getState();
        String userid = approval.getUserid();
        TaskList taskList = new TaskList();
        taskList.setCode(code);
        taskList.setUserid(userid);
        taskList.setOpinion(approval.getOpinion());
        taskList.setTime(Util.GetTime());
        taskList.setState(state);
        taskList.setMarke("此记录为用餐审批结果！");
        if (app.equals(approval.getUserid())) {
            Long order = eatDao.GetOrder(code);
            taskList.setOrderNum(order);
            taskListDao.UpdateTask(taskList);
            if (state.equals("2")) {
                UtilModel utilModel = taskListDao.GetUserid(code);
                if (utilModel == null) {
                    eatDao.Yes(code);
                } else {
                    utilModel.setData(utilModel.getCode());
                    utilModel.setCode(code);
                    eatDao.App(utilModel);
                }
                json.put("code", 200);
                json.put("mes", "审批成功！");
            } else if (state.equals("3")) {
                taskListDao.RefuseTask(approval);
                eatDao.No(code);
                json.put("mes", "审批成功");
                json.put("code", 500);
            } else {
                json.put("code", 500);
                json.put("mes", "审批状态有误！请重新审批！");
            }
            return json;
        } else {
            json.put("code", 500);
            json.put("mes", "当前审批人不为您！请检查！");
            return json;
        }
    }
}