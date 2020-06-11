package com.hr.controller;

import com.alibaba.fastjson.JSONObject;
import com.hr.service.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "/power")
public class PowerController {

    @Autowired
    private PowerService powerService;

    @RequestMapping(value = "/role")
    public String role() {
        return "power/role";
    }

    @RequestMapping(value = "/roleUpdate")
    public String roleUpdate() {
        return "power/update";
    }

    @RequestMapping(value = "/add")
    public String add() {
        return "power/add";
    }

    @ResponseBody
    @RequestMapping(value = "/AddData")
    public JSONObject AddData(@RequestParam Map<String,Object> map){
        return powerService.AddData(map);
    }

    @ResponseBody
    @RequestMapping(value = "/UpdateRole")
    public JSONObject UpdateRole(@RequestParam Map<String, Object> map) {
        return powerService.UpdateRole(map);
    }

    @ResponseBody
    @RequestMapping(value = "/roleList")
    public JSONObject roleList() {
        return powerService.list();
    }

    @ResponseBody
    @RequestMapping(value = "/GetList")
    public JSONObject GetList() {
        return powerService.GetList();
    }

    @ResponseBody
    @RequestMapping(value = "/GetOne")
    public JSONObject GetOne(String id) {
        return powerService.GetOne(id);
    }

    @RequestMapping(value = "/fenpei")
    public String fenpei() {
        return "power/fenpei";
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public JSONObject list(String userid) {
        return null;
    }
}
