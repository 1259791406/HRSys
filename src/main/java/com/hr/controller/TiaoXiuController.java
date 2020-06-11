package com.hr.controller;

import com.alibaba.fastjson.JSONObject;
import com.hr.model.Approval;
import com.hr.model.TiaoXiu;
import com.hr.service.TiaoXiuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/TiaoXiu")
public class TiaoXiuController {

    @Autowired
    private TiaoXiuService tiaoXiuService;

    @RequestMapping(value = "/index")
    public String index() {
        return "TiaoXiu/index";
    }

    @RequestMapping(value = "/Myindex")
    public String Myindex() {
        return "TiaoXiu/Myindex";
    }

    @RequestMapping(value = "/insert")
    public String insert() {
        return "TiaoXiu/insert";
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public JSONObject list(TiaoXiu tiaoXiu) {
        return tiaoXiuService.list(tiaoXiu);
    }

    @ResponseBody
    @RequestMapping(value = "/Add")
    public JSONObject Add(TiaoXiu tiaoXiu) {
        return tiaoXiuService.Add(tiaoXiu);
    }

    @ResponseBody
    @RequestMapping(value = "/App")
    public JSONObject App(Approval approval) {
        return tiaoXiuService.App(approval);
    }

    @ResponseBody
    @RequestMapping(value = "/GetList")
    public JSONObject GetList(String userid) {
        return tiaoXiuService.GetList(userid);
    }
}
