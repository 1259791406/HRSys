package com.hr.controller;

import com.alibaba.fastjson.JSONObject;
import com.hr.model.Workbench;
import com.hr.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/work")
public class WorkbenchController {

    @Autowired
    private WorkService workService;

    @RequestMapping(value = "/index")
    public String index() {
        return "work/index";
    }

    @RequestMapping(value = "/insert")
    public String insert(){
        return "work/insert";
    }

    @RequestMapping(value = "/update")
    public String update(){
        return "worl/update";
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public JSONObject list(Workbench workbench){
        return workService.list(workbench);
    }

    @ResponseBody
    @RequestMapping(value = "/add")
    public JSONObject add(Workbench workbench){
        return workService.add(workbench);
    }

    @ResponseBody
    @RequestMapping(value = "/updateData")
    public JSONObject updateData(Workbench workbench){
        return workService.update(workbench);
    }
}