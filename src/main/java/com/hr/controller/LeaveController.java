package com.hr.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hr.dao.LeaveType;
import com.hr.model.Approval;
import com.hr.model.LeaveModel;
import com.hr.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/Leeave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private LeaveType leaveType;

    @RequestMapping(value = "/list")
    public String list() {
        return "Leeave/list";
    }

    @RequestMapping(value = "/add")
    public String add() {
        return "Leeave/Add";
    }

    @RequestMapping(value = "/App")
    public String App() {
        return "Leeave/App";
    }

    @RequestMapping(value = "/AppList")
    public String AppList() {
        return "Leeave/AppList";
    }

    /**
     * 所有请假类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/type")
    public JSONObject type() {
        JSONObject json = new JSONObject();
        json.put("code", 200);
        json.put("data", JSONArray.parseArray(JSON.toJSONString(leaveType.list())));
        json.put("mes", "信息获取成功！");
        return json;
    }

    /**
     * 请假列表
     * @param leaveModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/page")
    public JSONObject page(LeaveModel leaveModel) {
        return leaveService.page(leaveModel);
    }

    /**
     * 添加请假数据
     * @param leaveModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/AddData")
    public JSONObject AddData(LeaveModel leaveModel) {
        return leaveService.Add(leaveModel);
    }

    /**
     * 请假审批
     * @param approval
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/Approval")
    public JSONObject App(Approval approval) {
        return leaveService.Approval(approval);
    }
}
