package com.hr.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hr.Overall.KaoQinData;
import com.hr.Overall.UtilModel;
import com.hr.Util.Util;
import com.hr.dao.CalendarDao;
import com.hr.model.CalendarList;
import com.hr.model.Scheduling;
import com.hr.model.Visitor;
import com.hr.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class CalendarImpl implements CalendarService {

    @Autowired
    private CalendarDao calendarDao;

    /**
     * 列表查询
     *
     * @param calendarList
     * @return
     */
    @Override
    public JSONObject pageList(CalendarList calendarList) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        List<CalendarList> lists = calendarDao.page(calendarList);
        int page = calendarList.getPage();
        int Limit = calendarList.getLimit();
        for (CalendarList calendarList1 : lists) {
            calendarList1.setPage(page);
            calendarList1.setLimit(Limit);
            array.add(JSON.parseObject(JSON.toJSONString(calendarList1)));
        }
        json.put("code", 0);
        json.put("data", array);
        json.put("mes", "排班获取成功。");
        json.put("count", calendarDao.count(calendarList));
        return json;
    }

    @Override
    public JSONObject AddData(CalendarList calendarList) {
        JSONObject json = new JSONObject();
        calendarList.setState("1");
        calendarList.setTitle(calendarList.getUserId() + "");
        calendarDao.AddCaData(calendarList);
        json.put("code", 200);
        json.put("mes", "排班记录增加成功！");
        return json;
    }

    @Override
    public JSONObject GetSchedulingList(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        JSONObject result = new JSONObject();
        String time = map.get("year").toString() + "-" + map.get("month").toString() + "-01";
        List<String> list = calendarDao.GetDateList(time);
        for (int i = 0; i < list.size(); i++) {
            String date = list.get(i);
            List<Scheduling> schedulings = calendarDao.GetSchedulingList(date);
            JSONArray array = new JSONArray();
            for (Scheduling scheduling : schedulings) {
                array.add(JSON.parseObject(JSON.toJSONString(scheduling)));
            }
            json.put(date, array);
        }
        result.put("code", 200);
        result.put("data", json);
        return result;
    }

    /**
     * 修改一条排班记录
     *
     * @param calendarList 排班实体类
     * @return
     */
    @Override
    public JSONObject UpdateData(CalendarList calendarList) {
        JSONObject json = new JSONObject();
        if (Util.TimeDTime(calendarList.getStart(), calendarList.getEndTime())) {
            calendarDao.UpdateData(calendarList);
            json.put("code", 200);
            json.put("mes", "修改成功！");
            return json;
        } else {
            json.put("code", 500);
            json.put("mes", "开始时间不允许大于结束时间，请重新设置！");
            return json;
        }
    }

    /**
     * 根据 userid 获取未来一周上班得
     *
     * @param userid 用户ID
     * @return
     */
    @Override
    public JSONObject GetWeekUser(String userid) {
        JSONObject json = new JSONObject();
        json.put("code", 200);
        json.put("data", JSONArray.parseArray(JSON.toJSONString(calendarDao.GetWeekUser(userid))));
        json.put("mes", "获取成功！");
        return json;
    }

    /**
     * 获取某个人的考勤记录与考勤软件结合
     *
     * @param calendarList
     * @return
     */
    @Override
    public JSONObject GetKaoQinList(CalendarList calendarList) {
        JSONObject json = new JSONObject();
        calendarList.setPage(1);
        int size = calendarDao.size(calendarList);
        calendarList.setLimit(size);
        List<CalendarList> list = calendarDao.list(calendarList);
        //签到：1   签退：0
        List<UtilModel> utilModels = KaoQinData.DataList(calendarList.getUserId(), calendarList.getStart(), Util.GetTime());
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setPage(1);
            list.get(i).setLimit(size);
            String start = list.get(i).getStart();
            String end = list.get(i).getEndTime();
            String q = start.substring(0, 10);
            String w = end.substring(0, 10);
            if (Util.time(start, Util.GetTime())) {
                list.get(i).setReserve15("未知");
            } else {
                for (int a = 0; a < utilModels.size(); a++) {
                    String e = utilModels.get(a).getData();
                    String r = e.substring(0, 10);
                    String mes = utilModels.get(a).getMes();
                    if (r.equals(q)) {
                        if ("1".equals(mes)) {
                            if (Util.time(start, e) || start.equals(e)) {
                                list.get(i).setReserve15("正常");
                            } else {
                                list.get(i).setReserve15("迟到");
                                break;
                            }
                        } else if ("0".equals(mes)) {
                            if (Util.time(e, end) || end.equals(e)) {
                                list.get(i).setReserve15("正常");
                            } else {
                                list.get(i).setReserve15("早退");
                                break;
                            }
                        } else {
                            if (Util.time(q, Util.GetTime())) {
                                list.get(i).setReserve15("未工作");
                                break;
                            } else {
                                list.get(i).setReserve15("旷工");
                                break;
                            }
                        }
                    } else {
                        list.get(i).setReserve15("旷工");
                        break;
                    }
                }
            }
        }
        json.put("code", 200);
        json.put("data", JSONArray.parseArray(JSON.toJSONString(list)));
        json.put("mes", "您从：" + calendarList.getStart() + " 到：" + calendarList.getEndTime() + " 所有考勤记录获取成功！");
        return json;
    }

    /**
     * 查看今天是否有排班
     *
     * @param userid
     * @return
     */
    @Override
    public JSONObject GetDayIsTrue(String userid) {
        JSONObject json = new JSONObject();
        if (calendarDao.LookDayIsCa(userid) > 0) {
            String time = calendarDao.GetEndTime(userid);
            json.put("code", 200);
            json.put("data", time);
            json.put("mes", "您今天下班时间为：" + time);
            return json;
        } else {
            json.put("code", 500);
            json.put("mes", "您今天无排班记录！");
            return json;
        }
    }
}