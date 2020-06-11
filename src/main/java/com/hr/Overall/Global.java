package com.hr.Overall;

/**
 * 工具类
 */
public class Global {
    public double qq = 321421;
    //日志存放路径
    public static final String FilePath = "D:\\ErrorLog\\";

    //定时任务  每五秒执行一次
    public static final String time = "0/5 * *  * * ? ";

    //上次登陆时间距离现在不得大于分钟数
    public static final long size = 60;

    //是否开启限制登陆次数与时间
    public static final boolean flag = false;

    public static final boolean isLogin = false;

    //限制登陆次数，当是否开启限制时有用
    public static final int num = 5;

    //无Token 或 Token失效 重定向地址
    public static final String Url = "http://127.0.0.1:8080/login";

    public static final int Time = 7200;

    //用餐扣除金额
    public static final double price = 10.0;

    //夜班津贴
    public static final double YeBanPrice = 13;
}