package com.hr.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hr.Overall.Global;
import com.hr.Overall.KaoQinData;
import com.hr.Overall.UtilModel;
import com.hr.Util.*;
import com.hr.dao.JurisdictionDao;
import com.hr.dao.RoleDao;
import com.hr.dao.StaffDao;
import com.hr.dao.UserDao;
import com.hr.model.*;
import com.hr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.text.ParseException;
import java.util.*;

@Service
@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class UserImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JurisdictionDao jurisdictionDao;

    @Autowired
    private StaffDao stafffMap;
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private StaffUtil staffUtil;

    /**
     * 登陆接口
     *
     * @param map phone：手机号   password：密码
     * @return 登陆人实体类
     */
    @Override
    public JSONObject Login(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        String phone = map.get("phone").toString();
        String username = map.get("username").toString();
        map.put("phone", new String(AES.decode(new String(AES.decode(new String(AES.decode(map.get("phone").toString())))))));
        int size = userDao.isPhone(username);
        if (size > 0) {
            User user = userDao.Login(map);
            if (user == null) {
                if (Global.flag) {
                    UtilModel model = userDao.Num(phone);
                    int num = Integer.valueOf(model.getData());
                    int number = Global.num;
                    if ((number - 1) == num) {
                        userDao.UpdateState(phone);
                    }
                    if (num >= number) {
                        json.put("code", 500);
                        json.put("mes", "此账户已被锁定，请在 " + Util.getMoreThanAnHourTime(model.getCode(), Global.size) + " 后登陆！");
                    } else {
                        userDao.UpdateLoginTime(phone);
                        json.put("code", 500);
                        json.put("mes", "用户名或密码错误！请重试！");
                    }
                } else {
                    json.put("code", 500);
                    json.put("mes", "用户名或密码错误，请检查再试！");
                }
            } else {
                Jedis jedis = new Jedis("127.0.0.1");
                String uuid = Util.GetToken();
                Role role = roleDao.GetSign(user.getRoleId() + "");
                String roleSign = role.getSign();
                if (roleSign == "" || roleSign == null) {
                    json.put("code", 500);
                    json.put("issign", 2);
                    json.put("isMes", "您无任何菜单操作权限！请联系管理员确认！");
                    json.put("mes", "您无任何权限！请联系管理员确认你得权限信息！");
                    return json;
                }
                String[] sign = role.getSign().split("-");
                JSONArray array = new JSONArray();
                List<Jurisdiction> listList = new ArrayList<>();
                List<Jurisdiction> arrayList = new ArrayList<>();
                for (int i = 0; i < sign.length; i++) {
                    listList.add(jurisdictionDao.childs(Long.valueOf(sign[i])).get(0));
                }
                for (int a = 0; a < listList.size(); a++) {
                    arrayList.add(jurisdictionDao.childs(Long.valueOf(listList.get(a).getParatid())).get(0));
                }
                Set<Jurisdiction> ts = new HashSet<>();
                for (int h = 0; h < arrayList.size(); h++) {
                    ts.add(arrayList.get(h));
                }
                List<Jurisdiction> list = new ArrayList<>();
                for (Jurisdiction jurisdiction : ts) {
                    list.add(jurisdiction);
                }
                for (int x = 0; x < list.size(); x++) {
                    List<Jurisdiction> jurisdictionList = new ArrayList<>();
                    for (int y = 0; y < listList.size(); y++) {
                        if (list.get(x).getId() == Long.valueOf(listList.get(y).getParatid())) {
                            jurisdictionList.add(listList.get(y));
                        }
                    }
                    list.get(x).setChildMenus(jurisdictionList);
                }
                array.add(JSON.toJSON(list));
                jedis.set(uuid, user.toString(), "NX", "EX", 1000);
                userDao.ReLoginTimeNum(username);
                json.put("code", 200);
                json.put("mes", "登陆成功！");
                json.put("id", user.getUserid());
                json.put("user", user.getUsername());
                json.put("rolename", role.getName());
                json.put("data", uuid);
                json.put("token", uuid);
                json.put("uuid", uuid);
                json.put("sign", array);
            }
            return json;
        } else {
            json.put("code", 500);
            json.put("mes", "用户 " + username + " 不存在或已限制登陆！");
            return json;
        }
    }

    /**
     * 获取所有用户信息
     *
     * @param map
     * @return
     */
    @Override
    public JSONObject list(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        Map<String, Object> objectMap = Page(map);
        List<User> list = userDao.list(objectMap);
        for (User user : list) {
            array.add(JSON.parseObject(JSON.toJSONString(user)));
        }
        json.put("code", 0);
        json.put("mes", "信息获取成功！");
        json.put("count", userDao.GetSize(objectMap));
        json.put("data", array);
        return json;
    }

    @Override
    public JSONObject VisList() {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        List<ToolModal> list = userDao.VisList();
        for (ToolModal toolModal : list) {
            array.add(JSON.parseObject(JSON.toJSONString(toolModal)));
        }
        json.put("code", 200);
        json.put("data", array);
        json.put("mes", "获取用户信息成功！");
        return json;
    }

    @Override
    public JSONObject Add(User user) {
        JSONObject json = new JSONObject();
        if (userDao.GetPhoneNum(user) > 0) {
            json.put("code", 500);
            json.put("mes", "手机号：" + user.getPhone() + " 已存在！请勿重复添加！");
            return json;
        }
        userDao.Add(user);
        json.put("code", 200);
        json.put("mes", "添加成功！");
        return json;
    }

    /**
     * 获取某个用户信息
     *
     * @param userid 用户ID
     * @return
     */
    @Override
    public JSONObject GetOneUser(String userid) {
        JSONObject json = new JSONObject();
        User user = userDao.GetOneUser(userid);
        user.setPassword("????");
        json.put("code", 200);
        json.put("data", user);
        return json;
    }

    /**
     * 修改数据
     *
     * @param user
     * @return
     */
    @Override
    public JSONObject UpdateData(User user) throws ParseException {
        JSONObject json = new JSONObject();
        if (userDao.GetPhoneNum(user) > 0) {
            json.put("code", 500);
            json.put("mes", "手机号：" + user.getPhone() + " 已存在！请修改成其他号码！");
            return json;
        }
        Map<String, Object> map = new HashMap<>();
        System.err.println(user.getUserid());
        map.put("userid", user.getUserid());

        System.err.println(Util.format().getCode());
        map.put("start", Util.format().getCode().substring(0, 10) + " 00:00:00");
        System.err.println(Util.format().getData());
        map.put("end", Util.format().getData().replace("  ", " "));
        System.err.println("map           " + map);
        List<Salary> salary = stafffMap.getSalary(map);
        System.err.println("salary                              " + salary);
        if (salary != null && !salary.isEmpty()) {
            for (Salary salary2 : salary) {
                switch (salary2.getState()) {
                    case "1":
                        Util(map, user);
                        json.put("code", 200);
                        json.put("mes", "修改用户：" + user.getUsername() + " 信息成功！");
                        return json;
                    case "2":
                        json.put("code", 500);
                        json.put("mes", "不允许修改此人信息！原因：此人当月工资已审批！");
                        return json;
                    case "3":
                        Util(map, user);
                        json.put("mes", "修改用户：" + user.getUsername() + " 信息成功！");
                        json.put("code", 200);
                        return json;
                    default:
                        json.put("code", 500);
                        json.put("mes", "审批状态有误！请检查！");
                        return json;
                }
            }
            json.put("code", 200);
            json.put("mes", "成功！");
            return json;
        } else {
            userDao.UpdateUser(user);
            Util(map, user);
            json.put("code", 200);
            json.put("mes", "修改用户：" + user.getUsername() + " 信息成功！");
            return json;
        }
    }

    public void Util(Map<String, Object> map, User user) throws ParseException {
        stafffMap.delete(map);
        userDao.UpdateUser(user);
        userDao.UpdateUser(user);
        staffUtil.setSalary(map);

    }

    /**
     * 用来查询所有当月未参与排班人员的信息
     *
     * @return
     */
    @Override
    public JSONObject SelectUserCa(String orderid) {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) - 1;
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        List<String> list1 = userDao.DayMonth(orderid);
        List<String> list2 = userDao.LastMonth(orderid);
        List<String> time1 = Util.getDayListOfMonth(month);
        List<String> time2 = Util.getDayListOfMonth(month + 1);
        for (int i = 0; i < time1.size(); i++) {
            String time = time1.get(i);
            if (!list1.contains(time)) {
                array.add(time);
            }
        }
        for (int i = 0; i < time2.size(); i++) {
            String time = time2.get(i);
            if (!list2.contains(time)) {
                array.add(time);
            }
        }
        json.put("code", 200);
        json.put("data", array);
        json.put("mes", "信息查询成功。");
        return json;
    }

    /**
     * 获取所有需要排班的人员列表
     *
     * @return
     */
    @Override
    public JSONObject SelectUserList() {
        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("data", JSONArray.parseArray(JSON.toJSONString(userDao.SelectUserList())));
        return json;
    }

    @Override
    public JSONObject DeptUserList(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", 200);
        json.put("data", JSONArray.parseArray(JSON.toJSONString(userDao.DeptUserList(map))));
        json.put("mes", "数据获取成功!");
        return json;
    }

    @Override
    public JSONObject GetPass(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        User user = userDao.Login(map);
        if (user != null) {
            map.put("password", map.get("username"));
            userDao.UpdatePas(map);
            json.put("code", 200);
            json.put("mes", "密码修改成功！正在登陆！");
        } else {
            json.put("code", 500);
            json.put("mes", "原密码错误！请检查！");
        }
        return json;
    }

    /**
     * 同步用户数据
     *
     * @return
     */
    @Override
    public JSONObject synchro() {
        JSONObject json = new JSONObject();
        List<User> list = userDao.SumList();
        List<User> userList = KaoQinData.GetUserList();
        int size = 0;
        for (int i = 0; i < userList.size(); i++) {
            String userid = userList.get(i).getUserid();
            for (int a = 0; a < list.size(); a++) {
                String id = list.get(a).getUserid();
                if (userid.equals(id)) {
                    break;
                } else {
                    size = size + 1;
                    userDao.Add(userList.get(i));
                    break;
                }
            }
        }
        json.put("code", 200);
        json.put("mes", "同步成功！新增：" + size + " 人");
        return json;
    }

    /**
     * 获取权限信息
     *
     * @param userid
     * @return
     */
    @Override
    public JSONObject power(String userid) {
        JSONObject json = new JSONObject();

        json.put("code", 200);
        json.put("user", "");
        json.put("data", "");
        json.put("mes", "获取权限成功！");
        return json;
    }

    /**
     * 查询条件
     *
     * @param map
     * @return
     */
    public static Map<String, Object> Page(Map<String, Object> map) {
        String data = "";
        if (map.containsKey("username")) {
            String marke = map.get("username").toString();
            if (!marke.equals("") && marke != null) {
                data += "AND username LIKE '%" + marke + "%' ";
            }
        }
        if (map.containsKey("state")) {
            String marke = map.get("state").toString();
            if (!marke.equals("") && marke != null) {
                data += "AND state = " + marke + " ";
            }
        }
        if (map.containsKey("phone")) {
            String marke = map.get("phone").toString();
            if (!marke.equals("") && marke != null) {
                data += "AND phone LIKE '%" + marke + "%' ";
            }
        }
        if (map.containsKey("idcard")) {
            String marke = map.get("idcard").toString();
            if (!marke.equals("") && marke != null) {
                data += "AND idcard LIKE '%" + marke + "%' ";
            }
        }
        if (map.containsKey("sex")) {
            String marke = map.get("sex").toString();
            if (!marke.equals("") && marke != null) {
                data += "AND sex = " + marke + " ";
            }
        }
        map.put("data", data);
        return map;
    }
}