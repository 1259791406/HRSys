package com.hr.model;

import java.util.List;

public class Reimbursement extends PageUtil{

    private long id;
    private String code;
    private String userid;
    private String username;
    private String type;
    private double price;
    private String time;
    private String app;
    private String appTime;
    private String marke;
    private List<ReimDetail> list;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }


    public String getAppTime() {
        return appTime;
    }

    public void setAppTime(String appTime) {
        this.appTime = appTime;
    }


    public String getMarke() {
        return marke;
    }

    public void setMarke(String marke) {
        this.marke = marke;
    }

    public List<ReimDetail> getList() {
        return list;
    }

    public void setList(List<ReimDetail> list) {
        this.list = list;
    }
}