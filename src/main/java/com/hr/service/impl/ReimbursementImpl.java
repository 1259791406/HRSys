package com.hr.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hr.Overall.UtilModel;
import com.hr.Util.Util;
import com.hr.dao.ReimbursementDao;
import com.hr.dao.TaskDao;
import com.hr.dao.TaskListDao;
import com.hr.model.*;
import com.hr.service.ReimbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class ReimbursementImpl implements ReimbursementService {

    @Autowired
    private ReimbursementDao reimbursementDao;

    @Autowired
    private TaskListDao taskListDao;

    @Autowired
    private TaskDao taskDao;

    @Override
    public JSONObject list(Reimbursement reimbursement) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        if (reimbursement.getUsername() == "") {
            reimbursement.setUsername(null);
        }
        if (reimbursement.getType() == "") {
            reimbursement.setType(null);
        }
        List<Reimbursement> list = reimbursementDao.list(reimbursement);
        int page = reimbursement.getPage();
        int Limit = reimbursement.getLimit();
        int count = reimbursementDao.count(reimbursement);
        for (Reimbursement re : list) {
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
    public JSONObject Add(Reimbursement reimbursement) {
        JSONObject json = new JSONObject();
        String code = Util.GetCode();
        reimbursement.setCode(code);
        String userid = reimbursement.getUserid();
        List<Task> list = taskDao.TaskList(new ToolModal(userid, "10005"));
        reimbursement.setApp(list.get(0).getUserid() + "");
        int a = reimbursementDao.Add(reimbursement);
        for (int i = 0; i < list.size(); i++) {
            TaskList taskList = new TaskList();
            taskList.setUserid(list.get(i).getUserid() + "");
            taskList.setCode(code);
            taskList.setType(list.get(i).getCode());
            taskList.setOrderNum(list.get(i).getOrderNum());
            taskListDao.AddData(taskList);
        }
        List<ReimDetail> reimDetailList = reimbursement.getList();
        for (int i = 0; i < reimDetailList.size(); i++) {
            reimDetailList.get(i).setCode(code);
            String q = reimDetailList.get(i).getMarke();
            String w = reimDetailList.get(i).getType();
            String e = reimDetailList.get(i).getStartTime();
            String r = reimDetailList.get(i).getEndTime();
            String t = reimDetailList.get(i).getSubjectCode();
            String y = reimDetailList.get(i).getCost();
            String u = reimDetailList.get(i).getTaxes();
            String o = reimDetailList.get(i).getPrice();
            if (q == "" && w == "" && e == "" && r == "" && t == "" && y == "" && u == "" && o == "") {
                System.out.println("此条数据无需添加！");
            } else {
                reimbursementDao.AddDe(reimDetailList.get(i));
            }
        }
        json.put("code", 200);
        json.put("data", "已提交申请，请等待审批完成！");
        return json;
    }

    @Override
    public JSONObject App(Approval approval) {
        JSONObject json = new JSONObject();
        String state = approval.getState();
        String code = approval.getCode();
        String userid = approval.getUserid();
        TaskList taskList = new TaskList();
        taskList.setUserid(userid);
        taskList.setCode(code);
        taskList.setOpinion(approval.getOpinion());
        taskList.setTime(Util.GetTime());
        taskList.setState(state);
        taskList.setMarke("此记录为报销审批结果！");
        String app = reimbursementDao.GetApp(code);
        if (app.equals(userid)) {
            Long order = reimbursementDao.GetOrder(code);
            taskList.setOrderNum(order);
            taskListDao.UpdateTask(taskList);
            if (state.equals("2")) {
                UtilModel utilModel = taskListDao.GetUserid(code);
                if (utilModel == null) {
                    reimbursementDao.Yes(code);
                } else {
                    utilModel.setData(utilModel.getCode());
                    utilModel.setCode(code);
                    reimbursementDao.update(utilModel);
                }
                json.put("code", 200);
                json.put("mes", "审批成功！");
            } else if (state.equals("3")) {
                taskListDao.RefuseTask(approval);
                reimbursementDao.No(code);
                json.put("code", 500);
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
    public JSONObject type() {
        JSONObject json = new JSONObject();
        json.put("code", 200);
        json.put("data", reimbursementDao.model());
        return json;
    }

    @Override
    public JSONObject detil(String code) {
        JSONObject json = new JSONObject();
        List<ReimDetail> list = reimbursementDao.reList(code);
        json.put("code", 0);
        json.put("data", list);
        json.put("mes", "获取成功！");
        return json;
    }
}
