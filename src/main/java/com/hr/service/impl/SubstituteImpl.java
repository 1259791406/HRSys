package com.hr.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hr.Overall.UtilModel;
import com.hr.Util.TaskUtil;
import com.hr.Util.Util;
import com.hr.dao.*;
import com.hr.model.*;
import com.hr.service.SubstituteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class SubstituteImpl implements SubstituteService {

    @Autowired
    private SubstituteDao substituteDao;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private TaskListDao taskListDao;

    @Autowired
    private OvertimeDao overtimeDao;

    @Autowired
    private CalendarDao calendarDao;

    @Autowired
    private UserDao userDao;

    @Override
    public JSONObject list(Substitute substitute) throws Exception {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        List<Substitute> list = substituteDao.list(substitute);
        int page = substitute.getPage();
        int limit = substitute.getLimit();
        for (Substitute substitute1 : list) {
            substitute1.setPage(page);
            substitute1.setLimit(limit);
            array.add(JSON.parseObject(JSON.toJSONString(substitute1)));
        }
        json.put("code", 0);
        json.put("data", array);
        json.put("count", substituteDao.count(substitute));
        json.put("mes", "获取所有替班信息成功！");
        return json;
    }

    @Override
    public JSONObject Add(Substitute substitute) {
        JSONObject json = new JSONObject();
        String table_id = Util.GetUUID();
        substitute.setTableId(table_id);
        substituteDao.AddData(substitute);
        String userid = substitute.getStartUserid();
        List<Task> list = taskDao.TaskList(new ToolModal(userid, "10002"));
        for (int i = 0; i < list.size(); i++) {
            TaskList taskList = new TaskList();
            taskList.setUserid(list.get(i).getUserid() + "");
            taskList.setCode(table_id);
            taskList.setOrderNum(list.get(i).getOrderNum());
            taskList.setType(list.get(i).getCode());
            taskListDao.AddData(taskList);
        }
        json.put("code", 200);
        json.put("mes", "您已申请替班成功！请等待审批完成！");
        return json;
    }

    @Override
    public JSONObject Approval(Approval approval) {
        JSONObject json = new JSONObject();
        //单据code
        String code = approval.getCode();
        //审批人ID
        String userid = approval.getUserid();
        //审批意见
        String Opinion = approval.getOpinion();
        //审批时间
        String time = Util.GetTime();
        //审批状态
        long state = Long.valueOf(approval.getState());
        TaskList task = taskListDao.GetOne(code);
        if (state == 3) {
            approval.setOrderNum(task.getOrderNum() + "");
            taskListDao.RefuseTask(approval);
            substituteDao.ApprovalNo(code);
            json.put("code", "200");
            json.put("mes", "单据编号：" + code + " 已拒绝！");
            return json;
        } else if (state == 1 || state == 2) {
            if (substituteDao.AppSize(code) > 0) {
                if (userid.equals(task.getUserid())) {
                    TaskList taskList = new TaskList();
                    taskList = TaskUtil.taskListUtil(taskList, code, time, state, Opinion, task.getOrderNum());
                    taskListDao.UpdateTask(taskList);
                    if (taskListDao.GetSize(code) == 0) {
                        substituteDao.ApprovalYes(code);
                        Substitute substitute = substituteDao.GetOne(code);
                        String startUserid = substitute.getStartUserid();
                        CalendarList calendarList = new CalendarList();
                        calendarList.setId(Long.valueOf(substitute.getStr1()));
                        calendarList.setUserId(startUserid);
                        calendarList.setReserve6("原排班记录为：" + userDao.GetUserMes(startUserid) + " ,现排班记录为：" + userDao.GetUserMes(substitute.getEndUserid()));
                        calendarDao.UpdateSub(calendarList);
                        json.put("code", 200);
                        json.put("mes", "单据已终审完毕！请刷新排班记录查看！");
                        return json;
                    } else {
                        UtilModel utilModel = taskListDao.GetUserid(code);
                        utilModel.setData(code);
                        substituteDao.UpdateApp(utilModel);
                        json.put("code", 200);
                        json.put("mes", "您已审批成功！");
                        return json;
                    }
                } else {
                    json.put("code", 500);
                    json.put("mes", "当前单据审批人不是您！请查看审批流程表！");
                    return json;
                }
            } else {
                json.put("code", 500);
                json.put("mes", "您所审批得单据，已审批结束或单据出现异常情况！");
                return json;
            }
        } else {
            json.put("code", 500);
            json.put("mes", "审批状态码有误！请检查！");
            return json;
        }
    }

    @Override
    public JSONObject GetSubCaList(String userid) {
        JSONObject json = new JSONObject();
        json.put("code", 200);
        json.put("data", JSONArray.parseArray(JSON.toJSONString(substituteDao.GetSubCaList(userid))));
        json.put("mes", "获取所有工作时间成功!");
        return json;
    }
}