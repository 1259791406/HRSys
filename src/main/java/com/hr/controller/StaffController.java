package com.hr.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hr.Overall.UtilModel;
import com.hr.Util.Util;
import com.hr.dao.SalaryDao;
import com.hr.dao.StaffDao;
import com.hr.dao.TaskListDao;
import com.hr.model.*;
import com.hr.service.impl.StaffUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/salary")
public class StaffController {

    @Autowired
    private StaffDao staffMap;

    @Autowired
    private StaffUtil staffUtil;

    @Autowired
    private TaskListDao taskListDao;

    @Autowired
    private SalaryDao salaryDao;

    @RequestMapping(value = "/my")
    public String my() {
        return "/salary/my";
    }

    @RequestMapping(value = "/MyApp")
    public String MyApp() {
        return "/salary/MyApp";
    }

    @RequestMapping(value = "/all")
    public String all() {
        return "/salary/all";
    }

    @RequestMapping(value = "/AllApp")
    public String AllApp() {
        return "/salary/AllApp";
    }

    @RequestMapping(value = "/updateIndex")
    public String update() {
        return "/salary/Update";
    }

    @ResponseBody
    @RequestMapping(value = "/updateData")
    public JSONObject updateData(Salary salary) {
        JSONObject json = new JSONObject();
        if (salaryDao.updateSalary(salary) > 0) {
            json.put("code", 200);
            json.put("mes", "修改成功！");
        } else {
            json.put("code", 500);
            json.put("mes", "修改失败！");
        }
        return json;
    }

    @ResponseBody
    @RequestMapping(value = "/pageList")
    public JSONObject pageList(@RequestParam Map<String, Object> map) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        if (map.containsKey("username")) {
            map.put("data", " AND username like '%" + map.get("username").toString() + "%'");
        }
        List<Salary> list = staffMap.pageList(map);
        for (Salary salary1 : list) {
            salary1.setPage(1);
            salary1.setLimit(1);
            array.add(JSON.parseObject(JSON.toJSONString(salary1)));
        }
        json.put("code", 0);
        json.put("mes", "succes");
        json.put("data", array);
        json.put("count", list.size());
        return json;
    }

    @ResponseBody
    @RequestMapping(value = "/sumSal")
    public JSONObject sumSal(@RequestParam Map<String, Object> map) throws ParseException {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        if (map.containsKey("cxtime")) {
            String time = map.get("cxtime").toString();
            map.put("end", Util.getLastDayOfMonth(time) + " 23:59:59");
            map.put("start", time + "-01 00:00:00");
        }
        List<Salary> salary = staffUtil.sumSalary(map);
        int page = Integer.valueOf(map.get("page").toString());
        int limit = Integer.valueOf(map.get("limit").toString());
        for (Salary list : salary) {
            list.setPage(page);
            list.setLimit(limit);
            array.add(JSONArray.parseObject(JSON.toJSONString(list)));
        }
        json.put("code", 0);
        json.put("data", array);
        json.put("count", salary.size());
        json.put("mes", "信息查询完毕");
        return json;
    }

    @ResponseBody
    @RequestMapping(value = "/App")
    public JSONObject App(Approval approval) {
        JSONObject json = new JSONObject();
        String code = approval.getCode();
        String userid = approval.getUserid();
        String state = approval.getState();
        UtilModel utilModel = taskListDao.GetUserid(code);
        if (userid.equals(utilModel.getCode())) {
            long orderNum = taskListDao.GetOne(code).getOrderNum();
            approval.setOrderNum(orderNum + "");
            TaskList taskList = new TaskList();
            taskList.setOpinion(approval.getOpinion());
            taskList.setState(approval.getState());
            taskList.setTime(Util.GetTime());
            taskList.setMarke("");
            taskList.setCode(code + "");
            taskList.setOrderNum(orderNum);
            taskListDao.UpdateTask(taskList);
            if (state.equals("2")) {
                int size = taskListDao.GetSize(code);
                if (size > 0) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("app", taskListDao.GetUserid(code).getCode());
                    map.put("code", code);
                    staffMap.App(map);
                    json.put("code", 200);
                    json.put("mes", "您已同意成功！");
                    return json;
                } else {
                    staffMap.YesApp(code);
                    json.put("code", 200);
                    json.put("mes", "您已同意成功！");
                    return json;
                }
            } else {
                taskListDao.RefuseTask(approval);
                staffMap.noApp(code);
                json.put("code", 200);
                json.put("mes", "您已拒绝成功！");
                return json;
            }
        } else {
            json.put("code", 500);
            json.put("mes", "当前单据不为您审批！请检查！");
            return json;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/page")
    public JSONObject page(Salary salary) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        List<Salary> list = staffMap.list(salary);
        int page = salary.getPage();
        int limit = salary.getLimit();
        for (Salary salary1 : list) {
            salary1.setPage(page);
            salary1.setLimit(limit);
            array.add(JSON.parseObject(JSON.toJSONString(salary1)));
        }
        json.put("code", 0);
        json.put("mes", "succes");
        json.put("data", array);
        json.put("count", staffMap.size(salary));
        return json;
    }
}