package com.hr.Overall;

public class UtilModel {
    private String code;
    private String data;
    private String mes;

    public UtilModel() {
    }

    public UtilModel(String code, String data, String mes) {
        this.code = code;
        this.data = data;
        this.mes = mes;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    @Override
    public String toString() {
        return "UtilModel{" +
                "code：" + code +
                ", data：" + data +
                ", mes：" + mes +
                '}';
    }
}