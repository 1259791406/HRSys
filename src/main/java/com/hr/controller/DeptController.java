package com.hr.controller;

import com.alibaba.fastjson.JSONObject;
import com.hr.model.Tree;
import com.hr.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @RequestMapping(value = "/page")
    public String page() {
        return "Dept/page";
    }

    @RequestMapping(value = "/Add")
    public String Add() {
        return "Dept/Add";
    }

    @ResponseBody
    @RequestMapping(value = "/AddDept")
    public JSONObject AddDept(Tree tree) {
        return deptService.Add(tree);
    }

    @ResponseBody
    @RequestMapping(value = "/DeList")
    public JSONObject DeList(Tree t) {
        return deptService.DeList(t);
    }

    @ResponseBody
    @RequestMapping(value = "/synchro")
    public JSONObject synchro(){
        return deptService.synchro();
    }
}