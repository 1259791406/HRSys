package com.hr.model;

/**
 * 工具实体类
 */
public class ToolModal {
    private String id;
    private String userid;
    private String code;
    private String name;
    private String time;
    private String state;
    private String marke;

    public ToolModal() {

    }

    public ToolModal(String userid, String code) {
        this.userid = userid;
        this.code = code;
    }

    public ToolModal(String id, String userid, String code, String name, String time, String state, String marke) {
        this.id = id;
        this.userid = userid;
        this.code = code;
        this.name = name;
        this.time = time;
        this.state = state;
        this.marke = marke;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMarke() {
        return marke;
    }

    public void setMarke(String marke) {
        this.marke = marke;
    }
}
