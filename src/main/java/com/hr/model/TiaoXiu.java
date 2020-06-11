package com.hr.model;


public class TiaoXiu extends PageUtil{

  private long id;
  private String userid;
  private String username;
  private String code;
  private String startTime;
  private double size;
  private String app;
  private String make;
  private long state;
  private String addTime;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }


  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }


  public double getSize() {
    return size;
  }

  public void setSize(double size) {
    this.size = size;
  }


  public String getApp() {
    return app;
  }

  public void setApp(String app) {
    this.app = app;
  }


  public String getMake() {
    return make;
  }

  public void setMake(String make) {
    this.make = make;
  }


  public long getState() {
    return state;
  }

  public void setState(long state) {
    this.state = state;
  }

  public String getAddTime() {
    return addTime;
  }

  public void setAddTime(String addTime) {
    this.addTime = addTime;
  }
}
