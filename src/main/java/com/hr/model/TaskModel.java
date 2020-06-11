package com.hr.model;

import java.util.List;

public class TaskModel {
    private String type;
    private String dept;
    private List<Task> list;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public List<Task> getList() {
        return list;
    }

    public void setList(List<Task> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "type='" + type + '\'' +
                ", dept='" + dept + '\'' +
                ", list=" + list +
                '}';
    }
}