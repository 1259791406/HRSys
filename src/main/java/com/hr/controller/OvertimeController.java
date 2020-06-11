package com.hr.controller;

import com.alibaba.fastjson.JSONObject;
import com.hr.model.Approval;
import com.hr.model.OverType;
import com.hr.model.Overtime;
import com.hr.model.PageUtil;
import com.hr.service.OvertimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "/Overtime")
public class OvertimeController {

    @Autowired
    private OvertimeService overtimeService;

    @RequestMapping(value = "/ApprovalList")
    public String ApprovalList(){
        return "/overtime/ApprovalList";
    }

    //加班申请类型集合
    @ResponseBody
    @RequestMapping(value = "/TypeList")
    public JSONObject TypeList(OverType overType) {
        return overtimeService.TypeList(overType);
    }

    //所有加班信息表
    @ResponseBody
    @RequestMapping(value = "/list")
    public JSONObject list(Overtime overtime) throws Exception {
        return overtimeService.page(overtime);
    }

    //增加一条加班信息表
    @ResponseBody
    @RequestMapping(value = "/AddData")
    public JSONObject AddData(Overtime overtime) {
        return overtimeService.AddData(overtime);
    }

    @ResponseBody
    @RequestMapping(value = "/h5list")
    public JSONObject h5list(Overtime overtime) {
        return overtimeService.h5list(overtime);
    }

    @ResponseBody
    @RequestMapping(value = "/Approval")
    public JSONObject Approval(Approval approval) {
        return overtimeService.Approval(approval);
    }

    @ResponseBody
    @RequestMapping(value = "/IsApproval")
    public JSONObject IsApproval(@RequestParam Map<String, Object> map) {
        return overtimeService.IsApproval(map);
    }
}