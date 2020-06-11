package com.hr.controller;

import com.alibaba.fastjson.JSONObject;
import com.hr.model.Approval;
import com.hr.model.Eat;
import com.hr.service.EatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/eat")
public class EatController {

    @Autowired
    private EatService eatService;

    @RequestMapping(value = "/page")
    public String page() {
        return "eat/page";
    }

    @RequestMapping(value = "/add")
    public String add() {
        return "eat/add";
    }

    @ResponseBody
    @RequestMapping(value = "App")
    public JSONObject App(Approval approval){
        return eatService.App(approval);
    }

    @ResponseBody
    @RequestMapping(value = "/Insert")
    public JSONObject add(Eat eat) {
        return eatService.Add(eat);
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    private JSONObject list(Eat eat) {
        return eatService.list(eat);
    }
}
