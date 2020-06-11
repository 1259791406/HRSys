package com.hr.controller;

import com.alibaba.fastjson.JSONObject;
import com.hr.model.Approval;
import com.hr.model.Visitor;
import com.hr.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "/Visitor")
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    /**
     * 全部访客申请页
     *
     * @return
     */
    @RequestMapping(value = "/page")
    public String page() {
        return "Visitor/page";
    }

    /**
     * 访客申请页审批页面
     *
     * @return
     */
    @RequestMapping(value = "/ApprovalPage")
    public String Approval() {
        return "Visitor/Approval";
    }

    /**
     * 访客申请全部审批页面
     *
     * @return
     */
    @RequestMapping(value = "/ApprovalList")
    public String ApprovalList() {
        return "Visitor/ApprovalList";
    }

    /**
     * 访客申请详情页
     *
     * @param visitor
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/VisitorPage")
    public JSONObject VisitorPage(Visitor visitor) throws Exception {
        return visitorService.Page(visitor);
    }

    /**
     * 增加访客申请页面
     *
     * @return
     */
    @RequestMapping(value = "/Add")
    public String Add() {
        return "Visitor/Add";
    }

    /**
     * 访客详情页面
     *
     * @return
     */
    @RequestMapping(value = "/One")
    public String One() {
        return "Visitor/One";
    }

    /**
     * 增加一条访客申请
     *
     * @param visitor
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/AddData")
    public JSONObject AddData(Visitor visitor) {
        return visitorService.Add(visitor);
    }

    /**
     * 访客类型
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/TypeList")
    public JSONObject TypeList() {
        return visitorService.TypeList();
    }

    /**
     * 访客申请单据审批！
     *
     * @param approval
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/Approval")
    public JSONObject Approval(Approval approval) {
        return visitorService.Approval(approval);
    }

    @ResponseBody
    @RequestMapping(value = "/info")
    public JSONObject info(@RequestParam Map<String, Object> map) {
        return visitorService.info(map);
    }
}
