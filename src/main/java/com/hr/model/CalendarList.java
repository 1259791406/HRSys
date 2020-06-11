package com.hr.model;

/**
 * 排班表
 */
public class CalendarList extends PageUtil {
    private long id;
    private String userId;
    private String day;
    private String type;
    private String title;
    private String allDay;
    private String start;
    private String endTime;
    private String url;
    private String className;
    private String editable;
    private String source;
    private String color;
    private String backgroundColor;
    private String borderColor;
    private String textColor;
    private String state;
    private String time;
    private long reserve1;
    private long reserve2;
    private long reserve3;
    private long reserve4;
    private long reserve5;
    private String reserve6;
    private String reserve7;
    private String reserve8;
    private String reserve9;
    private String reserve10;
    private String reserve11;
    private String reserve12;
    private String reserve13;
    private String reserve14;
    private String reserve15;
    private double reserve16;
    private double reserve17;
    private double reserve18;
    private double reserve19;
    private double reserve20;
    private String marke;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        if (userId == "") {
            this.userId = null;
        }else {
            this.userId = userId;
        }
    }


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getAllDay() {
        return allDay;
    }

    public void setAllDay(String allDay) {
        this.allDay = allDay;
    }


    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }


    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }


    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }


    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public long getReserve1() {
        return reserve1;
    }

    public void setReserve1(long reserve1) {
        this.reserve1 = reserve1;
    }


    public long getReserve2() {
        return reserve2;
    }

    public void setReserve2(long reserve2) {
        this.reserve2 = reserve2;
    }


    public long getReserve3() {
        return reserve3;
    }

    public void setReserve3(long reserve3) {
        this.reserve3 = reserve3;
    }


    public long getReserve4() {
        return reserve4;
    }

    public void setReserve4(long reserve4) {
        this.reserve4 = reserve4;
    }


    public long getReserve5() {
        return reserve5;
    }

    public void setReserve5(long reserve5) {
        this.reserve5 = reserve5;
    }


    public String getReserve6() {
        return reserve6;
    }

    public void setReserve6(String reserve6) {
        this.reserve6 = reserve6;
    }


    public String getReserve7() {
        return reserve7;
    }

    public void setReserve7(String reserve7) {
        this.reserve7 = reserve7;
    }


    public String getReserve8() {
        return reserve8;
    }

    public void setReserve8(String reserve8) {
        this.reserve8 = reserve8;
    }


    public String getReserve9() {
        return reserve9;
    }

    public void setReserve9(String reserve9) {
        this.reserve9 = reserve9;
    }


    public String getReserve10() {
        return reserve10;
    }

    public void setReserve10(String reserve10) {
        this.reserve10 = reserve10;
    }


    public String getReserve11() {
        return reserve11;
    }

    public void setReserve11(String reserve11) {
        this.reserve11 = reserve11;
    }


    public String getReserve12() {
        return reserve12;
    }

    public void setReserve12(String reserve12) {
        this.reserve12 = reserve12;
    }


    public String getReserve13() {
        return reserve13;
    }

    public void setReserve13(String reserve13) {
        this.reserve13 = reserve13;
    }


    public String getReserve14() {
        return reserve14;
    }

    public void setReserve14(String reserve14) {
        this.reserve14 = reserve14;
    }


    public String getReserve15() {
        return reserve15;
    }

    public void setReserve15(String reserve15) {
        this.reserve15 = reserve15;
    }


    public double getReserve16() {
        return reserve16;
    }

    public void setReserve16(double reserve16) {
        this.reserve16 = reserve16;
    }


    public double getReserve17() {
        return reserve17;
    }

    public void setReserve17(double reserve17) {
        this.reserve17 = reserve17;
    }


    public double getReserve18() {
        return reserve18;
    }

    public void setReserve18(double reserve18) {
        this.reserve18 = reserve18;
    }


    public double getReserve19() {
        return reserve19;
    }

    public void setReserve19(double reserve19) {
        this.reserve19 = reserve19;
    }


    public double getReserve20() {
        return reserve20;
    }

    public void setReserve20(double reserve20) {
        this.reserve20 = reserve20;
    }


    public String getMarke() {
        return marke;
    }

    public void setMarke(String marke) {
        this.marke = marke;
    }

    @Override
    public String toString() {
        return "CalendarList{" +
                "id：" + id +
                ", userId：" + userId +
                ", day：" + day +
                ", type：" + type +
                ", title：" + title +
                ", allDay：" + allDay +
                ", start：" + start +
                ", endTime：" + endTime +
                ", url：" + url +
                ", className：" + className +
                ", editable：" + editable +
                ", source：" + source +
                ", color：" + color +
                ", backgroundColor：" + backgroundColor +
                ", borderColor：" + borderColor +
                ", textColor：" + textColor +
                ", state：" + state +
                ", time：" + time +
                ", reserve1：" + reserve1 +
                ", reserve2：" + reserve2 +
                ", reserve3：" + reserve3 +
                ", reserve4：" + reserve4 +
                ", reserve5：" + reserve5 +
                ", reserve6：" + reserve6 +
                ", reserve7：" + reserve7 +
                ", reserve8：" + reserve8 +
                ", reserve9：" + reserve9 +
                ", reserve10：" + reserve10 +
                ", reserve11：" + reserve11 +
                ", reserve12：" + reserve12 +
                ", reserve13：" + reserve13 +
                ", reserve14：" + reserve14 +
                ", reserve15：" + reserve15 +
                ", reserve16：" + reserve16 +
                ", reserve17：" + reserve17 +
                ", reserve18：" + reserve18 +
                ", reserve19：" + reserve19 +
                ", reserve20：" + reserve20 +
                ", marke：" + marke +
                '}';
    }
}