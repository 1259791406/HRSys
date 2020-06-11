package com.hr.controller;

import com.alibaba.fastjson.JSONObject;
import com.hr.Overall.IPUtil;
import com.hr.Overall.FileUtil;
import com.hr.Util.Util;
import com.hr.dao.FileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 所有跳转页面的Controller
 */
@Controller
public class UrlController {

    @RequestMapping(value = "/photo")
    public String photo() {
        return "photo";
    }

    @RequestMapping(value = "/updateUser")
    public String updateUser() {
        return "updateUser";
    }

    /**
     * 登陆界面
     *
     * @return
     */
    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    /**
     * 登陆界面
     *
     * @return
     */
    @RequestMapping(value = "/h5login")
    public String h5login() {
        return "h5login";
    }

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    /**
     * 排班系统页面
     *
     * @return
     */
    @RequestMapping(value = "/calendar")
    public String calendar() {
        return "Calendar/calendar";
    }

    /**
     * 排班系统页面
     *
     * @return
     */
    @RequestMapping(value = "/calendarList")
    public String calendarList() {
        return "Calendar/index";
    }

    /**
     * 增加某人排班页面
     *
     * @return
     */
    @RequestMapping(value = "/AddCalendar")
    public String AddCalendar() {
        return "Calendar/AddCalendar";
    }

    /**
     * 修改某人排班页面
     *
     * @return
     */
    @RequestMapping(value = "/UpdateCalendar")
    public String UpdateCalendar() {
        return "Calendar/UpdateCalendar";
    }

    /**
     * 添加用户
     *
     * @return
     */
    @RequestMapping(value = "/userAdd")
    public String userAdd() {
        return "user/Add";
    }

    /**
     * 增加加班申请
     *
     * @return
     */
    @RequestMapping(value = "/overtimeAdd")
    public String overtimeAdd() {
        return "overtime/Add";
    }

    /**
     * 加班审批页面
     *
     * @return
     */
    @RequestMapping(value = "/overtimeApp")
    public String overtimeApp() {
        return "/overtime/Approval";
    }

    /**
     * 加班列表页面
     *
     * @return
     */
    @RequestMapping(value = "/overtimePage")
    public String overtimePage() {
        return "/overtime/page";
    }

    /**
     * 获取当日所有值班的用户页面
     *
     * @return
     */
    @RequestMapping(value = "/UserMes")
    public String UserMes() {
        return "user/UserMes";
    }

    /**
     * 获取访问者内网地址！
     *
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/GetPCIp")
    public JSONObject GetPCIp(HttpServletRequest request) {
        String ipone = IPUtil.getIpAddress(request);
        String Two = IPUtil.getLocalHostIP();
        JSONObject json = new JSONObject();
        if (ipone.equals("127.0.0.1")) {
            ipone = Two;
        }
        json.put("IP", ipone);
        json.put("WIP", request.getRemoteHost());
        return json;
    }

    /**
     * 申请替班页面
     *
     * @return
     */
    @RequestMapping(value = "/Substitute")
    public String Substitute() {
        return "substitute/page";
    }

    /**
     * 申请审批页面
     *
     * @return
     */
    @RequestMapping(value = "/SubstituteApp")
    public String SubstituteApp() {
        return "substitute/Approvall";
    }

    /**
     * 增加申请替班页面
     *
     * @return
     */
    @RequestMapping(value = "/AddSubstitute")
    public String AddSubstitute() {
        return "substitute/Add";
    }

    @RequestMapping(value = "/h5index")
    public String h5index() {
        return "phone/indedx";
    }

    @RequestMapping(value = "/salary")
    public String salary() {
        return "phone/salary";
    }

    @RequestMapping(value = "/FangKe")
    public String FangKe() {
        return "phone/FangKe";
    }

    @RequestMapping(value = "/KaoQin")
    public String KaoQin() {
        return "phone/KaoQin";
    }

    @RequestMapping(value = "/Shenpi")
    public String Shenpi() {
        return "phone/ShenPi";
    }

    @RequestMapping(value = "/PaiBanList")
    public String PaiBanList() {
        return "phone/PaiBanList";
    }

    @RequestMapping(value = "/JiaBan")
    public String JiaBan() {
        return "phone/JiaBan";
    }
}