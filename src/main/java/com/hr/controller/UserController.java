package com.hr.controller;

import com.alibaba.fastjson.JSONObject;
import com.hr.model.User;
import com.hr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Map;

/**
 * 用户信息
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户页面
     *
     * @return
     */
    @RequestMapping(value = "/page")
    public String userList() {
        return "user/userList";
    }

    /**
     * 修改某个用户信息
     *
     * @return
     */
    @RequestMapping(value = "/update")
    public String update() {
        return "user/Update";
    }

    /**
     * 修改数据
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/UpdateData")
    public JSONObject UpdateData(User user) throws ParseException {
        return userService.UpdateData(user);
    }

    /**
     * 获取某个人的信息
     *
     * @param userid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/GetOneUser")
    public JSONObject GetOneUser(String userid) {
        return userService.GetOneUser(userid);
    }

    /**
     * 用户登陆接口
     *
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "UserLogin")
    public JSONObject UserLogin(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        return userService.Login(map);
    }

    /**
     * 获取所有用户列表
     *
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/UserList")
    public JSONObject UserList(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        return userService.list(map);
    }

    @ResponseBody
    @RequestMapping(value = "/VisList")
    public JSONObject VisList() {
        return userService.VisList();
    }


    @ResponseBody
    @RequestMapping(value = "/Add")
    public JSONObject Add(User user) {
        return userService.Add(user);
    }

    /**
     * 用来查询所有当月未参与排班人员的信息
     */
    @ResponseBody
    @RequestMapping(value = "/SelectUserCa")
    public JSONObject SelectUserCa(String orderid) {
        return userService.SelectUserCa(orderid);
    }

    /**
     * 获取所有需要排班的人员列表
     */
    @ResponseBody
    @RequestMapping(value = "/SelectUserList")
    public JSONObject SelectUserList() {
        return userService.SelectUserList();
    }

    @ResponseBody
    @RequestMapping(value = "/DeptUserList")
    public JSONObject DeptUserList(@RequestParam Map<String, Object> map) {
        return userService.DeptUserList(map);
    }

    @ResponseBody
    @RequestMapping(value = "/GetPass")
    public JSONObject GetPass(@RequestParam Map<String, Object> map) {
        return userService.GetPass(map);
    }

    @ResponseBody
    @RequestMapping(value = "/synchro")
    public JSONObject synchro() {
        return userService.synchro();
    }

    @ResponseBody
    @RequestMapping(value = "/power")
    public JSONObject power(String userid) {
        return userService.power(userid);
    }
}