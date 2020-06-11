package com.hr.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hr.Overall.UtilModel;
import com.hr.Util.Util;
import com.hr.dao.OvertimeDao;
import com.hr.dao.TaskDao;
import com.hr.dao.TaskListDao;
import com.hr.model.*;
import com.hr.service.OvertimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class OvertimeImpl implements OvertimeService {

    @Autowired
    private OvertimeDao overtimeDao;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private TaskListDao taskListDao;

    /**
     * 加班类型
     *
     * @param overType
     * @return
     */
    @Override
    public JSONObject TypeList(OverType overType) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        List<OverType> list = overtimeDao.list();
        for (OverType o : list) {
            o.setPage(1);
            o.setLimit(1);
            array.add(JSON.parseObject(JSON.toJSONString(o)));
        }
        json.put("code", 200);
        json.put("data", array);
        return json;
    }

    /**
     * 加班查询
     *
     * @param overtime
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject page(Overtime overtime) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        int limit = overtime.getLimit();
        int page = overtime.getPage();
        if (overtime.getType() == "") {
            overtime.setType(null);
        }
        if (overtime.getUserid() == "") {
            overtime.setUserid(null);
        }
        List<Overtime> list = overtimeDao.page(overtime);
        for (Overtime overtime1 : list) {
            overtime1.setPage(page);
            overtime1.setLimit(limit);
            array.add(JSON.parseObject(JSON.toJSONString(overtime1)));
        }
        json.put("code", 0);
        json.put("data", array);
        json.put("count", overtimeDao.count(overtime));
        json.put("mes", "所有加班申请列表获取成功！");
        return json;
    }

    /**
     * 加班单据增加数据！
     *
     * @param overtime
     * @return
     */
    @Override
    public JSONObject AddData(Overtime overtime) {
        JSONObject json = new JSONObject();
        String code = Util.GetCode();
        overtime.setCode(code);
        double size = Double.valueOf(overtime.getEndTime());
        overtime.setEndTime(Util.getMoreThanAnHourTime(overtime.getStartTime(), (long) (size * 60)));
        String userid = overtime.getUserid();
        List<Task> list = taskDao.TaskList(new ToolModal(userid, "10000"));
        overtime.setApproval(list.get(0).getUserid() + "");
        int a = overtimeDao.AddData(overtime);
        for (int i = 0; i < list.size(); i++) {
            TaskList taskList = new TaskList();
            taskList.setCode(code);
            taskList.setUserid(list.get(i).getUserid() + "");
            taskList.setOrderNum(list.get(i).getOrderNum());
            taskList.setType(list.get(i).getCode());
            taskListDao.AddData(taskList);
        }
        json.put("code", 200);
        json.put("data", "已提交申请，请等待审批完成！");
        return json;
    }

    /**
     * 加班单据审批信息
     *
     * @param approval
     * @return
     */
    @Override
    public JSONObject Approval(Approval approval) {
        JSONObject json = new JSONObject();
        TaskList taskList = new TaskList();
        String code = approval.getCode();
        long state = Long.valueOf(approval.getState());
        taskList.setOpinion(approval.getOpinion());
        taskList.setState(state + "");
        taskList.setTime(Util.GetTime());
        taskList.setMarke("");
        taskList.setCode(code);
        taskList.setOrderNum(Long.valueOf(approval.getOrderNum()));
        taskListDao.UpdateTask(taskList);
        if (state == 3) {
            taskListDao.RefuseTask(approval);
            overtimeDao.NoOver(code);
        } else {
            UtilModel utilModel = taskListDao.GetUserid(code);
            if (utilModel == null) {
                overtimeDao.YesOver(code);
            } else {
                utilModel.setData(utilModel.getCode());
                utilModel.setCode(code);
                overtimeDao.UpdateApp(utilModel);
            }
        }
        json.put("code", 200);
        json.put("mes", "审批成功！");
        return json;
    }

    /**
     * H5页面查询数据
     *
     * @param overtime
     * @return
     */
    @Override
    public JSONObject h5list(Overtime overtime) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            int page = overtime.getPage();
            int limit = overtime.getLimit();
            List<Overtime> list = overtimeDao.h5list(overtime);
            for (Overtime o : list) {
                o.setPage(page);
                o.setLimit(limit);
                array.add(JSON.parseObject(JSON.toJSONString(o)));
            }
            json.put("code", 200);
            json.put("data", array);
            json.put("size", overtimeDao.size(overtime));
            json.put("mes", "获取成功");
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", 500);
            json.put("mes", "发生异常！");
            return json;
        }
    }

    /**
     * 查看是否是本人审批！
     *
     * @param map
     * @return
     */
    @Override
    public JSONObject IsApproval(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        UtilModel utilModel = taskListDao.GetUserid(map.get("code").toString());
        if (utilModel != null) {
            String userid = utilModel.getCode();
            if (map.get("userid").toString().equals(userid)) {
                json.put("code", 200);
                json.put("num", utilModel.getData());
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
