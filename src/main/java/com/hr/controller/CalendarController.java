package com.hr.controller;

import com.alibaba.fastjson.JSONObject;
import com.hr.model.CalendarList;
import com.hr.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.Map;

@Controller
@RequestMapping(value = "/calendar")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    @RequestMapping(value = "/page")
    public String page() {
        return "/Calendar/page";
    }

    @ResponseBody
    @RequestMapping(value = "/pageList")
    public JSONObject pageList(CalendarList calendarList) throws Exception {
        return calendarService.pageList(calendarList);
    }

    @ResponseBody
    @RequestMapping(value = "/GetSchedulingList")
    public JSONObject GetSchedulingList(@RequestParam Map<String, Object> map) {
        return calendarService.GetSchedulingList(map);
    }

    @ResponseBody
    @RequestMapping(value = "/Add")
    public JSONObject Add(CalendarList calendarList) {
        return calendarService.AddData(calendarList);
    }

    @RequestMapping(value = "/Update")
    public String Update() {
        return "/Calendar/Update";
    }

    @ResponseBody
    @RequestMapping(value = "/UpdateData")
    public JSONObject UpdateData(CalendarList calendarList) {
        return calendarService.UpdateData(calendarList);
    }

    @ResponseBody
    @RequestMapping(value = "/SubstituteData")
    public JSONObject SubstituteData(CalendarList calendarList) {
        return calendarService.UpdateData(calendarList);
    }

    @ResponseBody
    @RequestMapping(value = "/GetWeekUser")
    public JSONObject GetWeekUser(String userid) {
        return calendarService.GetWeekUser(userid);
    }

    @ResponseBody
    @RequestMapping(value = "/GetKaoQinList")
    public JSONObject GetKaoQinList(CalendarList calendarList) throws ParseException {
        return calendarService.GetKaoQinList(calendarList);
    }

    @ResponseBody
    @RequestMapping(value = "/GetSalary")
    public JSONObject GetSalary(@RequestParam Map<String, Object> map) {
        return null;
    }

    /**
     * 查看今天是否有排班
     * @param userid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/GetDayIsTrue")
    public JSONObject GetDayIsTrue(String userid){
        return calendarService.GetDayIsTrue(userid);
    }
}