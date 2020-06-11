package com.hr.controller;

import com.alibaba.fastjson.JSONObject;
import com.hr.model.TaskModel;
import com.hr.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "/bill")
public class BillController {

    @Autowired
    private BillService billService;

    @RequestMapping(value = "/index")
    public String index(){
        return "bill/index";
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public JSONObject list() {
        return billService.list();
    }

    @ResponseBody
    @RequestMapping(value = "/DeptList")
    public JSONObject DeptList(String code) {
        return billService.DeptList(code);
    }

    @ResponseBody
    @RequestMapping(value = "/taskList")
    public JSONObject taskList(@RequestParam Map<String, String> map) {
        return billService.taskList(map);
    }

    @ResponseBody
    @RequestMapping(value = "/Add")
    public JSONObject Add(TaskModel list) {
        return billService.Add(list);
    }
}