package com.hr.model;


import com.alibaba.fastjson.JSONArray;

public class Role {

    private long id;
    private String name;
    private String sign;
    private long state;
    private String time;
    private String makre;
    private JSONArray data;

    public JSONArray getData() {
        return data;
    }

    public void setData(JSONArray data) {
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }


    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getMakre() {
        return makre;
    }

    public void setMakre(String makre) {
        this.makre = makre;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id：" + id +
                ", name：" + name + 
                ", sign：" + sign + 
                ", state：" + state +
                ", time：" + time + 
                ", makre：" + makre + 
                ", data：" + data +
                '}';
    }
}
