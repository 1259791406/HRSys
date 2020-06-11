package com.hr.controller;

import com.alibaba.fastjson.JSONObject;
import com.hr.service.TaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/task")
public class TaskListController {

    @Autowired
    private TaskListService taskListService;

    @ResponseBody
    @RequestMapping(value = "/GetTableList")
    public JSONObject GetTableList(String code) {
        return taskListService.GetTableList(code);
    }
}