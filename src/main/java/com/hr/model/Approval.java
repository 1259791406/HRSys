package com.hr.model;

import java.util.List;

/**
 * 审批工具类
 */
public class Approval {
    //审批单据CODE
    private String code;
    //审批人
    private String userid;
    //审批结果  2：同意   3：不同意
    private String state;
    //审批意见
    private String Opinion;
    //审批时间
    private String time;
    //审批类型
    private String type;
    //审批人次序
    private String orderNum;

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        try {
            int s = Integer.valueOf(state);
            if (s > 3) {
                throw new Exception("单据审批状态有误！请检查！");
            }else {
                this.state = state;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getOpinion() {
        return Opinion;
    }

    public void setOpinion(String opinion) {
        Opinion = opinion;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
