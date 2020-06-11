package com.hr.controller;

import com.alibaba.fastjson.JSONObject;
import com.hr.model.Approval;
import com.hr.model.Substitute;
import com.hr.service.SubstituteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/substitute")
public class SubstituteController {
    @Autowired
    private SubstituteService substituteService;

    @ResponseBody
    @RequestMapping(value = "/page")
    public JSONObject page(Substitute substitute) throws Exception {
        return substituteService.list(substitute);
    }

    @RequestMapping(value = "/Add")
    public String Add(){
        return "/substitute/Add";
    }

    @RequestMapping(value = "/ApprovallList")
    public String ApprovallList(){
        return "/substitute/ApprovallList";
    }

    @ResponseBody
    @RequestMapping(value = "/AddData")
    public JSONObject AddData(Substitute substitute) {
        return substituteService.Add(substitute);
    }

    @ResponseBody
    @RequestMapping(value = "/Approval")
    public JSONObject Approval(Approval approval) {
        return substituteService.Approval(approval);
    }

    @ResponseBody
    @RequestMapping(value = "/GetSubCaList")
    public JSONObject GetSubCaList(String userid){
        return substituteService.GetSubCaList(userid);
    }
}