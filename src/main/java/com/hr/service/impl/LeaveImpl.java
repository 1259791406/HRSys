package com.hr.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hr.Overall.UtilModel;
import com.hr.Util.Util;
import com.hr.dao.LeaveDao;
import com.hr.dao.TaskDao;
import com.hr.dao.TaskListDao;
import com.hr.model.*;
import com.hr.service.LeaveService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class LeaveImpl implements LeaveService {

    @Autowired
    private LeaveDao leaveDao;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private TaskListDao taskListDao;

    @Override
    public JSONObject page(LeaveModel leaveModel) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        List<LeaveModel> lists = leaveDao.page(leaveModel);
        int page = leaveModel.getPage();
        int Limit = leaveModel.getLimit();
        for (LeaveModel model : lists) {
            model.setPage(page);
            model.setLimit(Limit);
            array.add(JSON.parseObject(JSON.toJSONString(model)));
        }
        json.put("code", 0);
        json.put("data", array);
        json.put("mes", "加班获取成功。");
        json.put("count", leaveDao.count(leaveModel));
        return json;
    }

    @Override
    public JSONObject Add(LeaveModel leaveModel) {
        JSONObject json = new JSONObject();
        String code = Util.GetCode();
        leaveModel.setCode(code);
        leaveModel.setTime(Util.GetTime());
        List<Task> task = taskDao.TaskList(new ToolModal(leaveModel.getUserid(), "10003"));
        leaveModel.setApproval(task.get(0).getUserid() + "");
        leaveDao.Add(leaveModel);
        String table_code = task.get(0).getCode();
        for (int i = 0; i < task.size(); i++) {
            TaskList taskList = new TaskList();
            taskList.setCode(code);
            taskList.setUserid(task.get(i).getUserid() + "");
            taskList.setOrderNum(task.get(i).getOrderNum());
            taskList.setType(table_code);
            taskListDao.AddData(taskList);
        }
        json.put("code", 200);
        json.put("mes", "添加成功！");
        return json;
    }

    @Override
    public JSONObject Approval(Approval approval) {
        TaskList taskList = new TaskList();
        JSONObject json = new JSONObject();
        long state = Long.valueOf(approval.getState());
        String code = approval.getCode();
        String userid = approval.getUserid();
        UtilModel utilModel = taskListDao.GetUserid(code);
        if (utilModel != null) {
            if (userid.equals(utilModel.getCode())) {
                taskList.setCode(code);
                taskList.setState(state + "");
                taskList.setOpinion(approval.getOpinion());
                taskList.setTime(Util.GetTime());
                taskList.setOrderNum(Long.valueOf(utilModel.getData()));
                taskList.setMarke("");
                taskListDao.UpdateTask(taskList);
                if (state == 3) {
                    taskListDao.RefuseTask(approval);
                    leaveDao.NoApp(code);
                } else {
                    UtilModel model = taskListDao.GetUserid(code);
                    if (model == null) {
                        leaveDao.YesApp(code);
                    } else {
                        model.setData(model.getCode());
                        model.setCode(code);
                        leaveDao.UpdateApp(model);
                    }
                }
                json.put("code", 200);
                json.put("mes", "审批成功！");
                return json;
            } else {
                json.put("code", 500);
                json.put("mes", "当前审批人不为您！");
                return json;
            }
        } else {
            json.put("code", 500);
            json.put("mes", "获取当前审批人失败！");
            return json;
        }
    }
}
