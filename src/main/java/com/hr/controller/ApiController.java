package com.hr.controller;

import com.hr.Overall.KaoQinData;
import com.hr.Overall.UtilModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 系统API接口
 * 版本 v 1.0
 */
@Controller
@RequestMapping(value = "/v1/Api")
public class ApiController {

    @ResponseBody
    @RequestMapping(value = "kqList")
    public List<UtilModel> list(@RequestParam Map<String, String> map) {
        return KaoQinData.DataList(map.get("userid"), map.get("start"), map.get("end"));
    }
}