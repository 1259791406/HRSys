package com.hr.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hr.Util.TaskUtil;
import com.hr.Util.Util;
import com.hr.dao.CalendarDao;
import com.hr.dao.OvertimeDao;
import com.hr.dao.TaskListDao;
import com.hr.model.*;
import com.hr.service.ApprovalService;
import com.hr.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//所有审批得到方法
@Service
@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class ApprovalImpl implements ApprovalService {

    @Autowired
    private OvertimeDao overtimeDao;

    @Autowired
    private TaskListDao taskListDao;

    @Autowired
    private CalendarDao calendarDao;

    @Autowired
    private VisitorService visitorService;

    @Override
    public JSONObject Data(Approval approval) {
        JSONObject json = new JSONObject();
        String type = approval.getType();
        String code = approval.getCode();
        String userid = approval.getUserid();
        String Opinion = approval.getOpinion();
        String time = Util.GetTime();
        long state = Long.valueOf(approval.getState());
        switch (state + "") {
            case "2":
                break;
            case "3":
                break;
            default:
                json.put("mes", "审批状态数据错误！请检查或重新再试！");
                json.put("code", 500);
                return json;
        }
        switch (type) {
            //加班
            case "Over":
                TaskList task = taskListDao.GetOne(code);
                if (state == 3) {
                    approval.setOrderNum(task.getOrderNum() + "");
                    taskListDao.RefuseTask(approval);
                    overtimeDao.NoOver(code);
                }
                if (overtimeDao.AppSize(code) > 0) {
                    if (userid.equals(task.getUserid())) {
                        TaskList taskList = new TaskList();
                        taskList = TaskUtil.taskListUtil(taskList, code, time, state, Opinion, task.getOrderNum());
                        taskListDao.UpdateTask(taskList);
                        if (taskListDao.GetSize(code) == 0) {
                            overtimeDao.YesOver(code);
                            CalendarList calendarList = new CalendarList();
                            Overtime overtime = overtimeDao.GetOne(code);
                            calendarList = TaskUtil.calendarListUtil(calendarList, userid, overtime.getStartTime(), overtime.getEndTime());
                            calendarDao.AddCaData(calendarList);
                            json.put("code", 200);
                            json.put("mes", "单据已终审完毕！请查看排班记录！");
                            return json;
                        }
                    } else {
                        json.put("code", 500);
                        json.put("mes", "当前单据审批人不是您！请查看审批流程表！");
                        return json;
                    }
                } else {
                    json.put("code", 500);
                    json.put("mes", "您所审批得单据，已不支持审核！请检查单据状态！");
                    return json;
                }
            case "asd":
                break;
            default:
                json.put("code", "500");
                json.put("mes", "抱歉，您所提交得单据类型！暂不支持审批！");
                break;
        }
        json.put("code", 200);
        json.put("mes", "您已审批成功！");
        return json;
    }
}
