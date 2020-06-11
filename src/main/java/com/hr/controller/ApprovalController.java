package com.hr.controller;

import com.alibaba.fastjson.JSONObject;
import com.hr.model.Approval;
import com.hr.service.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/Approval")
public class ApprovalController {

    @Autowired
    private ApprovalService approvalService;

    @ResponseBody
    @RequestMapping(value = "/data")
    public JSONObject data(Approval approval) {
        return approvalService.Data(approval);
    }
}
