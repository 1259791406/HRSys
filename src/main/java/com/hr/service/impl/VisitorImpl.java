package com.hr.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hr.Overall.UtilModel;
import com.hr.Util.Util;
import com.hr.dao.TaskDao;
import com.hr.dao.TaskListDao;
import com.hr.dao.VisitorDao;
import com.hr.model.*;
import com.hr.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class VisitorImpl implements VisitorService {

    @Autowired
    private VisitorDao visitorDao;

    @Autowired
    private TaskListDao taskListDao;

    @Autowired
    private TaskDao taskDao;

    /**
     * 增加一条访客申请记录
     *
     * @param visitor
     * @return
     */
    @Override
    public JSONObject Add(Visitor visitor) {
        JSONObject json = new JSONObject();
        String uuid = Util.GetUUID();
        visitor.setTableId(uuid);
        visitor.setfId(uuid);
        List<Task> list = taskDao.TaskList(new ToolModal(visitor.getUserId(), "10000"));
        visitor.setApproval(list.get(0).getUserid() + "");
        visitorDao.AddData(visitor);
        for (int i = 0; i < list.size(); i++) {
            TaskList taskList = new TaskList();
            taskList.setCode(uuid);
            taskList.setUserid(list.get(i).getUserid() + "");
            taskList.setType(list.get(i).getCode());
            taskList.setOrderNum(list.get(i).getOrderNum());
            taskListDao.AddData(taskList);
        }
        json.put("code", 200);
        json.put("mes", "访客已申请，请等待审批完成！");
        return json;
    }

    @Override
    public JSONObject Page(Visitor visitor) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        if (visitor.getUserId() == "") {
            visitor.setUserId(null);
        }
        if (visitor.getVisitType() == "") {
            visitor.setVisitType(null);
        }
        int size = visitorDao.count(visitor);
        if (visitor.getMarke().equals("all")) {
            visitor.setLimit(size);
        }
        int page = visitor.getPage();
        List<Visitor> list = visitorDao.Page(visitor);
        for (Visitor visitor1 : list) {
            visitor1.setPage(page);
            visitor1.setLimit(visitor.getLimit());
            array.add(JSON.parseObject(JSON.toJSONString(visitor1)));
        }
        json.put("code", 0);
        json.put("data", array);
        json.put("count", size);
        json.put("mes", "数据获取成功！");
        return json;
    }

    /**
     * 所有来访类型表
     *
     * @return
     */
    @Override
    public JSONObject TypeList() {
        JSONObject json = new JSONObject();
        json.put("code", 200);
        json.put("data", JSONArray.parseArray(JSON.toJSONString(visitorDao.Type())));
        json.put("mes", "所有来访类型获取成功！");
        return json;
    }

    /**
     * 审批
     *
     * @param approval
     * @return
     */
    @Override
    public JSONObject Approval(Approval approval) {
        JSONObject json = new JSONObject();
        String state = approval.getState();
        String code = approval.getCode();
        String userid = approval.getUserid();
        TaskList taskList = new TaskList();
        UtilModel model = taskListDao.GetUserid(code);
        taskList.setCode(code);
        taskList.setUserid(userid);
        taskList.setOrderNum(Long.valueOf(model.getData()));
        taskList.setOpinion(approval.getOpinion());
        taskList.setState(state);
        taskList.setTime(Util.GetTime());
        taskList.setMarke("此记录为访客申请审批结果！");
        String app = visitorDao.GetApp(code);
        if (app.equals(userid)) {
            taskListDao.UpdateTask(taskList);
            if (state.equals("2")) {
                UtilModel utilModel = taskListDao.GetUserid(code);
                if (utilModel == null) {
                    visitorDao.YesApp(code);
                } else {
                    utilModel.setData(utilModel.getCode());
                    utilModel.setCode(code);
                    visitorDao.UpdateApp(utilModel);
                }
                if(true){

                }
                json.put("code", 200);
                json.put("mes", "审批成功！请安保部门查看单据状态！");
            } else if (state.equals("3")) {
                taskListDao.RefuseTask(approval);
                visitorDao.NoApp(code);
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

    /**
     * 门卫设置进入、离开
     *
     * @param map
     * @return
     */
    @Override
    public JSONObject info(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        String type = map.get("type").toString();
        if (type.equals("1") || type.equals("2")) {
            String id = map.get("id").toString();
            Visitor visitor = visitorDao.GetOne(id);
            if (type.equals("1")) {
                String time = visitor.getSureGoTime();
                if (time == null || time == "") {
                    if (visitorDao.UpdateSureGo(id) > 0) {
                        json.put("code", 200);
                        json.put("mes", "操作成功！");
                        return json;
                    } else {
                        json.put("code", 500);
                        json.put("mes", "该人设置进入失败！");
                        return json;
                    }
                } else {
                    json.put("code", 500);
                    json.put("mes", "此人已进入！请勿重复点击！");
                    return json;
                }
            } else {
                String GoTime = visitor.getSureGoTime();
                if (GoTime == null || GoTime == "") {
                    json.put("code", 500);
                    json.put("mes", "此人未进入，请先选择进入！");
                    return json;
                } else {
                    String time = visitor.getSureOutTime();
                    if (time == null || time == "") {
                        if (visitorDao.UpdateSureOut(id) > 0) {
                            json.put("code", 200);
                            json.put("mes", "操作成功！");
                            return json;
                        } else {
                            json.put("code", 500);
                            json.put("mes", "该人设置离开失败！");
                            return json;
                        }
                    } else {
                        json.put("code", 500);
                        json.put("mes", "此人已离开！请勿重复点击！");
                        return json;
                    }
                }
            }
        } else {
            json.put("code", 500);
            json.put("mes", "类型错误！请检查数据问题！");
            return json;
        }
    }
}