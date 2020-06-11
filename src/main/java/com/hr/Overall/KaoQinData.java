package com.hr.Overall;

import com.hr.model.Tree;
import com.hr.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class KaoQinData {
    public static void ass() {
        System.out.println("sss");
    }

    public static List<UtilModel> DataList(String userid, String time, String time2) {
        try {
            String sql = "select * from CHECKINOUT where userid = " + userid + " and CHECKTIME >= #" + time + "# and checktime < #" + time2 + "# ";
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String url = "jdbc:odbc:AAA";
            Properties properties = new Properties();
            properties.put("user", "");
            properties.put("charSet", "gbk");
            properties.put("password", "");
            Connection con = DriverManager.getConnection(url, properties);
            Statement sta = con.createStatement();
            ResultSet rst = sta.executeQuery(sql);
            List<UtilModel> list = new ArrayList<>();
            while (rst.next()) {
                UtilModel utilModel = new UtilModel();
                utilModel.setCode(rst.getString("userid"));
                utilModel.setData(rst.getString("CHECKTIME"));
                String type = rst.getString("CHECKTYPE");
                if (type.equals("0") || type.equals("O") || type.equals("o")) {
                    //下班
                    type = "0";
                } else {
                    //上班
                    type = "1";
                }
                utilModel.setMes(type);
                list.add(utilModel);
                System.out.println(utilModel.toString());
            }
            return list;
        } catch (Exception e) {
            ErrorFileUtil.Ex(e);
            return null;
        }
    }

    public static List<User> GetUserList() {
        try {
            String sql = "select * from userinfo";
            ResultSet rst = GetSqlData(sql);
            List<User> list = new ArrayList<>();
            while (rst.next()) {
                User user = new User();
                user.setUsername(rst.getString("name"));
                String sex = rst.getString("GENDER");
                if ("M".equals(sex)) {
                    user.setSex("1");
                } else {
                    user.setSex("2");
                }
                user.setUserid(rst.getString("ssn"));
                user.setPhone(rst.getString("PAGER"));
                user.setDay(rst.getString("BIRTHDAY"));
                user.setAddress(rst.getString("street"));
                user.setSalaryCardNo(rst.getString("CardNo"));
                user.setDeptid(rst.getString("DEFAULTDEPTID"));
                user.setIdcard(rst.getString("idcardno"));
                user.setEmail(rst.getString("email"));
                user.setRoleId(0);
                System.out.println(user.toString());
                list.add(user);
            }
            return list;
        } catch (Exception e) {
            ErrorFileUtil.Ex(e);
            return null;
        }
    }

    public static List<Tree> GetDept() {
        try {
            List<Tree> list = new ArrayList<>();
            String sql = "SELECT * FROM DEPARTMENTS";
            ResultSet rst = GetSqlData(sql);
            while (rst.next()) {
                Tree tree = new Tree();
                tree.setTime(rst.getString("deptid"));
                tree.setTitle(rst.getString("deptname"));
                tree.setValue(rst.getString("supdeptid"));
                list.add(tree);
            }
            return list;
        } catch (Exception e) {
            ErrorFileUtil.Ex(e);
            return null;
        }
    }

    public static ResultSet GetSqlData(String sql) {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String url = "jdbc:odbc:AAA";
            Properties properties = new Properties();
            properties.put("charSet", "gbk");
            properties.put("user", "");
            properties.put("password", "");
            Connection con = DriverManager.getConnection(url, properties);
            Statement sta = con.createStatement();
            ResultSet rst = sta.executeQuery(sql);
            return rst;
        } catch (Exception e) {
            ErrorFileUtil.Ex(e);
            return null;
        }
    }

    public static UtilModel GetMaxMin(String userid, String time) throws SQLException {
        List<UtilModel> list = new ArrayList<>();
        String sql = "select * from CHECKINOUT where userid = " + userid + " and (DateDiff('d',CHECKTIME,'" + time.substring(2, 10) + "')=0)  ";
        ResultSet resultSet = GetSqlData(sql);
        while (resultSet.next()) {
            UtilModel utilModel = new UtilModel();
            utilModel.setCode(resultSet.getString("CHECKTIME"));
            String type = resultSet.getString("CHECKTYPE");
            if (type.equals("0") || type.equals("O") || type.equals("o")) {
                //下班
                type = "0";
            } else {
                //上班
                type = "1";
            }
            utilModel.setData(type);
            list.add(utilModel);
        }
        List<String> a = new ArrayList<>();
        List<String> b = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String code = list.get(i).getCode().replace("-", "").replace(" ", "").replace(":", "");
            if (list.get(i).getData().equals("1")) {
                a.add(code);
            } else {
                b.add(code);
            }
        }
        if (list.size() < 2) {
            return new UtilModel("1", "1", "");
        } else {
            UtilModel utilModel = new UtilModel();
            utilModel.setCode(Collections.min(a));
            utilModel.setData(Collections.max(b));
            return utilModel;
        }
    }

    public static List<String> TestUtil(String userid, String start, String end) {
        List<String> list = new ArrayList<>();
        try {
            list.add("2020-05-06 13:23:48");
            list.add("2020-05-30 23:59:20");
            list.add("2020-05-29 23:59:20");
            list.add("2020-05-28 23:59:20");
            list.add("2020-05-27 23:59:20");
            list.add("2020-05-26 23:59:20");
//            String sql = "select * from CHECKINOUT where userid = " + userid + " and CHECKTIME >= #" + start + "# and checktime < #" + end + "# ";
//            ResultSet resultSet = GetSqlData(sql);
//            while (resultSet.next()) {
//                list.add(resultSet.getString("CHECKTIME"));
//            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}