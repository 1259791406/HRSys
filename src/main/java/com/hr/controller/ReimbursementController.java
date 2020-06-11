package com.hr.controller;

import com.alibaba.fastjson.JSONObject;
import com.hr.model.Approval;
import com.hr.model.Reimbursement;
import com.hr.service.ReimbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/Reimbursement")
public class ReimbursementController {

    @Autowired
    private ReimbursementService reimbursementService;

    @RequestMapping(value = "/index")
    public String index() {
        return "Reimbursement/index";
    }

    @RequestMapping(value = "/Myindex")
    public String Myindex() {
        return "Reimbursement/Myindex";
    }

    @RequestMapping(value = "/insert")
    public String insert() {
        return "Reimbursement/insert";
    }

    @ResponseBody
    @RequestMapping(value = "/detil")
    public JSONObject detil(String code) {
        return reimbursementService.detil(code);
    }

    @RequestMapping(value = "/detilindex")
    public String detilIndex() {
        return "Reimbursement/detilindex";
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public JSONObject list(Reimbursement reimbursement) {
        return reimbursementService.list(reimbursement);
    }

    @ResponseBody
    @RequestMapping(value = "/Add")
    public JSONObject Add(Reimbursement reimbursement) {
        return reimbursementService.Add(reimbursement);
    }

    @ResponseBody
    @RequestMapping(value = "/App")
    public JSONObject App(Approval approval) {
        return reimbursementService.App(approval);
    }

    @ResponseBody
    @RequestMapping(value = "/type")
    public JSONObject type() {
        return reimbursementService.type();
    }

}
