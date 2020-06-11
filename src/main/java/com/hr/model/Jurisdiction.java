package com.hr.model;

import java.util.List;
import java.util.Objects;

public class Jurisdiction {

    private long id;
    private String name;
    private String url;
    private String icon;
    private String paratid;
    private long state;
    private String time;
    private String marke;
    private List<Jurisdiction> childMenus;


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


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    public String getParatid() {
        return paratid;
    }

    public void setParatid(String paratid) {
        this.paratid = paratid;
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


    public String getMarke() {
        return marke;
    }

    public void setMarke(String marke) {
        this.marke = marke;
    }

    public List<Jurisdiction> getChildMenus() {
        return childMenus;
    }

    public void setChildMenus(List<Jurisdiction> childMenus) {
        this.childMenus = childMenus;
    }

    @Override
    public String toString() {
        return "Jurisdiction{" +
                "id：" + id +
                ", name：" + name + 
                ", url：" + url + 
                ", icon：" + icon + 
                ", paratid：" + paratid + 
                ", state：" + state +
                ", time：" + time + 
                ", marke：" + marke + 
                ", childMenus：" + childMenus +
                '}';
    }
}