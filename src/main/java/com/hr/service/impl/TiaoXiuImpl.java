package com.hr.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hr.Overall.UtilModel;
import com.hr.Util.Util;
import com.hr.dao.TaskDao;
import com.hr.dao.TaskListDao;
import com.hr.dao.TiaoXiuDao;
import com.hr.dao.UtilDao;
import com.hr.model.*;
import com.hr.service.TiaoXiuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TiaoXiuImpl implements TiaoXiuService {

    @Autowired
    private TiaoXiuDao tiaoXiuDao;

    @Autowired
    private UtilDao utilDao;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private TaskListDao taskListDao;

    @Override
    public JSONObject list(TiaoXiu tiaoXiu) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        if (tiaoXiu.getUsername() == "") {
            tiaoXiu.setUsername(null);
        }
        if (tiaoXiu.getUserid() == "") {
            tiaoXiu.setUserid(null);
        }
        List<TiaoXiu> list = tiaoXiuDao.list(tiaoXiu);
        int page = tiaoXiu.getPage();
        int Limit = tiaoXiu.getLimit();
        int count = tiaoXiuDao.count(tiaoXiu);
        for (TiaoXiu re : list) {
            re.setPage(page);
            re.setLimit(Limit);
            array.add(JSON.parseObject(JSON.toJSONString(re)));
        }
        json.put("code", 0);
        json.put("data", array);
        json.put("mes", "报销申请获取成功！");
        json.put("count", count);
        return json;
    }

    @Override
    public JSONObject Add(TiaoXiu tiaoXiu) {
        String userid = tiaoXiu.getUserid();
        List<Task> list = taskDao.TaskList(new ToolModal(userid, "10006"));
        JSONObject json = new JSONObject();
        String code = Util.GetCode();
        tiaoXiu.setStartTime(tiaoXiu.getStartTime().substring(0, 19));
        tiaoXiu.setCode(code);
        tiaoXiu.setApp(list.get(0).getUserid() + "");
        int a = tiaoXiuDao.Add(tiaoXiu);
        for (int i = 0; i < list.size(); i++) {
            TaskList taskList = new TaskList();
            taskList.setUserid(list.get(i).getUserid() + "");
            taskList.setOrderNum(list.get(i).getOrderNum());
            taskList.setCode(code);
            taskList.setType(list.get(i).getCode());
            taskListDao.AddData(taskList);
        }
        json.put("code", 200);
        json.put("data", "已提交申请，请等待审批完成！");
        return json;
    }

    @Override
    public JSONObject App(Approval approval) {
        JSONObject json = new JSONObject();
        String userid = approval.getUserid();
        String state = approval.getState();
        String code = approval.getCode();
        TaskList taskList = new TaskList();
        taskList.setCode(code);
        taskList.setUserid(userid);
        taskList.setTime(Util.GetTime());
        taskList.setOpinion(approval.getOpinion());
        taskList.setMarke("此记录为调休审批结果！");
        taskList.setState(state);
        String app = tiaoXiuDao.GetApp(code);
        if (app.equals(userid)) {
            Long order = utilDao.GetOrder(code);
            taskList.setOrderNum(order);
            taskListDao.UpdateTask(taskList);
            if (state.equals("2")) {
                UtilModel utilModel = taskListDao.GetUserid(code);
                if (utilModel == null) {
                    tiaoXiuDao.Yes(code);
                } else {
                    utilModel.setData(utilModel.getCode());
                    utilModel.setCode(code);
                    tiaoXiuDao.update(utilModel);
                }
                json.put("mes", "审批成功！");
                json.put("code", 200);
            } else if (state.equals("3")) {
                taskListDao.RefuseTask(approval);
                json.put("code", 500);
                tiaoXiuDao.No(code);
                json.put("mes", "审批成功");
            } else {
                json.put("code", 500);
                json.put("mes", "审批状态有误！请重新审批！");
            }
            return json;
        } else {
            json.put("code", 500);
            json.put("mes", "当前单据不为您审批！请检查！");
            return json;
        }
    }

    @Override
    public JSONObject GetList(String userid) {
        JSONObject json = new JSONObject();
        List<UtilModel> list = tiaoXiuDao.dateList(userid);
        json.put("code", 200);
        json.put("data", list);
        json.put("mes", "成功！");
        return json;
    }
}