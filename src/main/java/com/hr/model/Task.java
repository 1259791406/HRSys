package com.hr.model;

public class Task {

    private long id;
    private long userid;
    private String code;
    private long state;
    private String addtime;
    private long orderNum;
    private String username;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
    }


    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(long orderNum) {
        this.orderNum = orderNum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", userid=" + userid +
                ", code='" + code + '\'' +
                ", state=" + state +
                ", addtime='" + addtime + '\'' +
                ", orderNum=" + orderNum +
                ", username='" + username + '\'' +
                '}';
    }
}
