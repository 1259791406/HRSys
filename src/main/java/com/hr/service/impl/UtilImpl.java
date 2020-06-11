package com.hr.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hr.Overall.ErrorFileUtil;
import com.hr.Overall.UtilModel;
import com.hr.Util.Util;
import com.hr.dao.CalendarDao;
import com.hr.dao.UserDao;
import com.hr.dao.UtilDao;
import com.hr.model.CalendarList;
import com.hr.model.ToolModal;
import com.hr.service.UtilService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class UtilImpl implements UtilService {

    @Autowired
    private UtilDao utilDao;

    @Autowired
    private CalendarDao calendarDao;

    @Autowired
    private UserDao userDao;

    @Override
    public JSONObject EducationList() {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        List<ToolModal> list = utilDao.EducationList();
        for (int i = 0; i < list.size(); i++) {
            array.add(JSON.parseObject(JSON.toJSONString(list.get(i))));
        }
        json.put("code", 200);
        json.put("mes", "获取所有学历列表成功。");
        json.put("data", array);
        return json;
    }

    @Override
    public JSONObject importUtil(MultipartFile file) throws Exception {
        JSONObject json = new JSONObject();
        XSSFWorkbook wb = null;
        InputStream input = null;
        input = file.getInputStream();
        wb = new XSSFWorkbook(input);
        JSONArray array = new JSONArray();
        for (int sheetNum = 0; sheetNum < wb.getNumberOfSheets(); sheetNum++) {
            XSSFSheet xssfSheet = wb.getSheetAt(sheetNum);
            if (xssfSheet == null) {
                continue;
            }
            int size = xssfSheet.getLastRowNum();
            if (size < 2) {
                json.put("code", 100);
                json.put("mes", "表中无数据，请填入数据在进行导入！");
                return json;
            }
            for (int rowNum = 1; rowNum < size + 1; rowNum++) {
                XSSFRow row = xssfSheet.getRow(rowNum);
                if (row != null) {
                    array.add(JSON.parseObject(JSON.toJSONString(importUtil(row))));
                }
            }
        }
        json.put("code", 200);
        json.put("data", array);
        json.put("mes", "导入已完成！");
        return json;
    }

    public UtilModel importUtil(XSSFRow xssfRow) throws Exception {
        UtilModel utilModel = new UtilModel();
        CalendarList calendarList = new CalendarList();
        String time = "";
        time = Util.GetTime();
        int size = xssfRow.getLastCellNum();
        for (int a = 0; a < size; a++) {
            Cell cell = xssfRow.getCell(a);
            String data = null;
            if (cell == null) {
                data = "";
            } else {
                data = ISNull(xssfRow.getCell(a).toString());
            }
            long start = Long.valueOf(new SimpleDateFormat("yyyyMMddHHmmss").format(xssfRow.getCell(1).getDateCellValue()));
            long end = Long.valueOf(new SimpleDateFormat("yyyyMMddHHmmss").format(xssfRow.getCell(2).getDateCellValue()));
            if (end < start) {
                utilModel.setCode("500");
                utilModel.setMes("第：" + (a + 1) + " 行，排班结束时间小于开始时间，请确认！");
                utilModel.setData(time);
                return utilModel;
            }
            if (a == 0) {
                if (userDao.isPhone(data) > 0) {
                    String userid = userDao.PhoneGetUserid(data);
                    calendarList.setUserId(userid);
                    calendarList.setTitle(userid);
                    calendarList.setState("1");
                    calendarList.setLimit(1);
                    calendarList.setPage(1);
                    calendarList.setMarke(Util.GetTime() + " 导入生成排班记录！");
                } else {
                    utilModel.setCode("500");
                    utilModel.setMes("手机号：" + data + "不存在！请检查！");
                    utilModel.setData(time);
                    return utilModel;
                }
            } else if (a == 1) {
                calendarList.setStart(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(xssfRow.getCell(1).getDateCellValue()));
            } else if (a == 2) {
                calendarList.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(xssfRow.getCell(2).getDateCellValue()));
            }
        }
        int i = calendarDao.AddCaData(calendarList);
        if (i > 0) {
            utilModel.setCode("200");
            utilModel.setMes("此人排班记录已成功添加！");
            utilModel.setData(time);
            return utilModel;
        } else {
            utilModel.setCode("500");
            utilModel.setMes("排班记录添加失败！");
            utilModel.setData(time);
            return utilModel;
        }

    }

    public static String ISNull(String data) {
        if (data.isEmpty()) {
            return "";
        } else {
            return data;
        }
    }
}
