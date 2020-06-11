package com.hr.model;

public class Tree extends PageUtil {
    private String value;
    private String title;
    private String time;
    private String marke;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @Override
    public String toString() {
        return "Tree{" +
                "value：" + value +
                ", title：" + title +
                ", time：" + time +
                ", marke：" + marke +
                '}';
    }
}
