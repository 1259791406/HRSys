package com.hr.Util;

import com.hr.Overall.UtilModel;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Util {

    public static String getLastDayOfMonth(String yearMonth) {
        int year = Integer.parseInt(yearMonth.split("-")[0]);  //年
        int month = Integer.parseInt(yearMonth.split("-")[1]); //月
        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        // cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.MONTH, month); //设置当前月的上一个月
        // 获取某月最大天数
        //int lastDay = cal.getActualMaximum(Calendar.DATE);
        int lastDay = cal.getMinimum(Calendar.DATE); //获取月份中的最小值，即第一天
        // 设置日历中月份的最大天数
        //cal.set(Calendar.DAY_OF_MONTH, lastDay);
        cal.set(Calendar.DAY_OF_MONTH, lastDay - 1); //上月的第一天减去1就是当月的最后一天
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    /**
     * 获取当前日期所在得星期
     *
     * @return 返回结果如下，当前日期为以下日期中某一天。
     * 2020-02-10 周一
     * 2020-02-11 周二
     * 2020-02-12 周三
     * 2020-02-13 周四
     * 2020-02-14 周五
     * 2020-02-15 周六
     * 2020-02-16 周日
     */
    @SuppressWarnings("deprecation")
    public static List<String> GetTodayWeek() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddEEE");
        Date mdate = new Date();
        List<String> strings = new ArrayList<>();
        int b = mdate.getDay();
        Date fdate;
        List<Date> list = new ArrayList<Date>();
        Long fTime = mdate.getTime() - b * 24 * 3600000;
        for (int a = 1; a <= 7; a++) {
            fdate = new Date();
            fdate.setTime(fTime + (a * 24 * 3600000));
            list.add(a - 1, fdate);
            strings.add(sdf.format(list.get(a - 1)));
        }
        return strings;
    }

    /**
     * 时间转换类
     *
     * @return 返回结果如下
     * 2019-02-12 21:44:42
     */
    public static String GetTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * 利用UUID来生成一个token
     *
     * @return
     */
    public static String Token() {
        return UUID.randomUUID().toString().replace("-", "0");
    }

    /**
     * 生成一个 code 号_16位
     *
     * @return 例子如下：
     * 1000000369728444
     */
    public static String GetCode() {
        int machineId = 1;
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {
            hashCodeV = -hashCodeV;
        }
        return machineId + String.format("%015d", hashCodeV);
    }

    /**
     * 利用UUID来生成一个token
     *
     * @return 返回结果如下
     * 8F4F6F7D-0379-4F39-9B89-06B3A2D5DF28
     */
    public static String GetUUID() {
        return UUID.randomUUID().toString().toUpperCase();
    }

    /**
     * 获取一个Token
     *
     * @return 返回结果如下
     * 14A2171B0EE74CE88EA9AE1B4394CB3E007C186F49704EA49F77197898F0135DBE9DC3D1B23D403A8D67F327A63CEE77
     */
    public static String GetToken() {
        String data = UUID.randomUUID().toString().toUpperCase().replace("-", "");
        data = data + UUID.randomUUID().toString().toUpperCase().replace("-", "");
        data = data + UUID.randomUUID().toString().toUpperCase().replace("-", "");
        return data;
    }

    /**
     * 生成一个ID号_16位
     *
     * @return 返回结果如下
     * 1000000945773906
     */
    public static String GetId() {
        int machineId = 1;
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {
            hashCodeV = -hashCodeV;
        }
        return machineId + String.format("%015d", hashCodeV);
    }

    /**
     * 获取某个时间增加多少毫秒的时间
     *
     * @param yourTime 被增加的时间数
     * @param size     需要增加的分钟
     * @return
     */
    public static String getMoreThanAnHourTime(String yourTime, long size) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String res = "";
        Date date;
        try {
            date = format.parse(yourTime);
            //获取传递进来的时间转为时间戳的值
            long time = date.getTime();
            //把该值加上一个一小时的毫秒数
            long anHour = size * 60 * 1000;
            long result = time + anHour;
            Date thedate = new Date(result);
            res = format.format(thedate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(new Date().getTime() + 60 * 60 * 1000));
        }
        return res;
    }

    public static boolean TimeDTime(String start, String end) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            //将字符串形式的时间转化为Date类型的时间
            Date a = sdf.parse(start);
            Date b = sdf.parse(end);
            if (a.getTime() > b.getTime()) {
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取当月所有天
     *
     * @return
     */
    public static List<String> getDayListOfMonth(int mon) {
        List<String> list = new ArrayList<String>();
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        aCalendar.set(aCalendar.MONTH, mon);
        int year = aCalendar.get(Calendar.YEAR);//年份
        int month = aCalendar.get(Calendar.MONTH) + 1;//月份
        String m = month > 9 ? month + "" : "0" + month;
        int day = aCalendar.getActualMaximum(Calendar.DATE);
        for (int i = 1; i <= day; i++) {
            String aDate = String.valueOf(year) + "-" + m + "-" + (i > 9 ? i : "0" + i);
            list.add(aDate);
        }
        return list;
    }

    /**
     * 判断请求是否手机端
     *
     * @param req
     * @return
     */
    public static boolean isMobile(HttpServletRequest req) {
        UserAgent ua = UserAgent.parseUserAgentString(req.getHeader("User-Agent"));
        OperatingSystem os = ua.getOperatingSystem();
        if (DeviceType.MOBILE.equals(os.getDeviceType())) {
            return true;
        }
        return false;
    }

    /**
     * 判断某个时间与现在时间的大小关系
     * 大于返回 true
     * 小于返回 false
     *
     * @param time
     * @return
     */
    public static boolean time(String time, String time2) {
        try {
            long a = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(time).getTime();
            long b = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(time2).getTime();
            return a > b ? true : false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static List<UtilModel> KaoQinData(){
        List<UtilModel> list = new ArrayList<>();
        list.add(new UtilModel("1","2020-05-04 20:19:00","0"));
        list.add(new UtilModel("1","2020-05-05 20:19:00","1"));
        list.add(new UtilModel("1","2020-05-01 11:01:00","1"));
        list.add(new UtilModel("1","2020-05-01 15:01:00","0"));
        return list;
    }

    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
    private static String firstDay;
    private static String lastDay;

    public static UtilModel format(){
        //获取前月的第一天
        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, -1);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        firstDay = format.format(cal_1.getTime());
        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH, 0);//设置为1号,当前日期既为本月第一天
        lastDay = format.format(cale.getTime());;
        return new UtilModel(format.format(cal_1.getTime()),format.format(cale.getTime()),"");
    }

}