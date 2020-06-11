package com.hr.service;

import com.alibaba.fastjson.JSONObject;
import com.hr.model.CalendarList;
import com.hr.model.Visitor;

import java.text.ParseException;
import java.util.Map;

public interface CalendarService {
    JSONObject pageList(CalendarList calendarList) throws Exception;

    JSONObject AddData(CalendarList calendarList);

    JSONObject GetSchedulingList(Map<String, Object> map);

    JSONObject UpdateData(CalendarList calendarList);

    JSONObject GetWeekUser(String userid);

    JSONObject GetKaoQinList(CalendarList calendarList) throws ParseException;

    JSONObject GetDayIsTrue(String userid);
}