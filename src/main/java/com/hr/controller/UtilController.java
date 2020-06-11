package com.hr.controller;

import com.alibaba.fastjson.JSONObject;
import com.hr.Util.Util;
import com.hr.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/util")
public class UtilController {

    @Autowired
    private UtilService utilService;

    @ResponseBody
    @RequestMapping(value = "/GetUUID")
    public String GetUUID() {
        return Util.GetUUID();
    }

    @ResponseBody
    @RequestMapping(value = "/GetUserId")
    public String GetUserId() {
        return Util.GetId();
    }

    @ResponseBody
    @RequestMapping(value = "/EducationList")
    public JSONObject EducationList() {
        return utilService.EducationList();
    }
}
