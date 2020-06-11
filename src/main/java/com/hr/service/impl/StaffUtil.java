package com.hr.service.impl;

import com.hr.Overall.Global;
import com.hr.Overall.KaoQinData;
import com.hr.Overall.UtilModel;

import com.hr.Util.HttpUtil;
import com.hr.Util.Util;
import com.hr.dao.StaffDao;
import com.hr.dao.TaskDao;
import com.hr.dao.TaskListDao;
import com.hr.dao.TiaoXiuDao;
import com.hr.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

@Service
@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class StaffUtil {

    @Autowired
    private StaffDao staffMap;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private TaskListDao taskListDao;

    @Autowired
    private TiaoXiuDao tiaoXiuDao;

    @Autowired
    private FiveUtil fiveUtil;


    public List<Salary> sumSalary(Map<String, Object> map) throws ParseException {
        //得到员工的基本工资等信息
        List<Salary> salary = staffMap.getSalary(map);
        if (salary != null && !salary.isEmpty()) {
            return salary;
        } else {
            setSalary(map);
            return staffMap.getSalary(map);
        }
    }


    /**
     * 计算工资明细并插入数据操作方法
     *
     * @param
     * @return
     */
    public Integer setSalary(Map<String, Object> map) throws ParseException {
        Salary salary = new Salary();

        double num = 0;
        double sum = 0;
        Map<String, Double> maps = new HashMap<>();
        String userid = map.get("userid").toString();
        User user = staffMap.getUser(userid);
        List<CalendarList> staff = staffMap.getFive(map);
        //应上班时长
        double rows = user.getSize();
        //实际上班时长
        double row = Double.valueOf(staffMap.countTime(userid));
        //计算每个小时的工资为多少
        Double rowss = user.getSalary() / rows;
        salary.setHour(rowss);
        double bis = over(map, rowss, salary);
        if (!user.getUserType().equals("1")) {
            // 判断他实际上班时间是否大于正常上班 时间
            if (row <= rows) {
                // 缺少的上班时间
                //需要扣除的金额
                num = (rows - row) * rowss;
            }
            salary.setHour(rowss);
        }
        Double duration = staffMap.getDuration(map);
        if (duration != null) {
            //请假需要扣除的钱
            sum = duration * 8 * rowss;
        }
        String start = map.get("start").toString();
        String end = map.get("end").toString();

        //计算吃饭需要扣除得工资
        double eat = fiveUtil.getDemos(staff, Global.price, KaoQinData.TestUtil(userid, start, end), map,user.getUserType());
        maps = FiveUtil.getDemo(staff, rowss, userid);

        // 1.先求和，算出工资总数x
        Double total = user.getSalary() + user.getAllowance() + bis - num;
        // 五险社保基数
        Double risks = getwuXian(total, salary);
        //专项扣除
        Double special = getzhuanxiang(user.getChildren(), user.getSupportTheOld(), user.getAdult(), user.getSerious(), user.getRenting(), user.getDraw());

        // 需要扣除的税款
        Double tax = getTax(total - special);
        salary.setOvertime_Pay(Double.valueOf(String.format("%.2f", tax)));
        // 实际工资
        total = total - (risks + tax) - (maps.get("late") + maps.get("early") + maps.get("absenteeism"));
        salary.setOvertime_Pay(tax);
        //数据库插入操作
        List<Task> task = taskDao.TaskList(new ToolModal(userid, "10004"));
        String code = Util.GetCode();
        salary.setApp(task.get(0).getUserid() + "");
        salary.setCode(code);
        String table_code = task.get(0).getCode();
        for (int i = 0; i < task.size(); i++) {
            TaskList taskList = new TaskList();
            taskList.setUserid(task.get(i).getUserid() + "");
            taskList.setCode(code);
            taskList.setOrderNum(task.get(i).getOrderNum());
            taskList.setType(table_code);
            taskListDao.AddData(taskList);
        }
        salary.setBasic(Double.valueOf(String.format("%.2f", user.getSalary())));
        //夜班津贴
        salary.setNight(0);
        salary.setMonthPerformance(Double.valueOf(String.format("%.2f", user.getMonthPerformance())));
        salary.setQuarterPerformance(Double.valueOf(String.format("%.2f", user.getQuarterPerformance())));
        salary.setYearEndBonus(Double.valueOf(String.format("%.2f", user.getYearEndBonus())));
        salary.setAllowance(Double.valueOf(String.format("%.2f", user.getAllowance())));
        // 特别奖项
        salary.setParticularlyAawards(0);
        // 差旅补贴
        salary.setFestival(0);
        //年节慰问
        salary.setTravel(0);

        salary.setLeave(Double.valueOf(String.format("%.2f", sum)));
        ;
        salary.setChildren(Double.valueOf(String.format("%.2f", user.getChildren())));
        salary.setSupportTheOld(Double.valueOf(String.format("%.2f", user.getSupportTheOld())));
        salary.setAdult(Double.valueOf(String.format("%.2f", user.getAdult())));
        salary.setDraw(Double.valueOf(String.format("%.2f", user.getDraw())));
        salary.setRenting(Double.valueOf(String.format("%.2f", user.getRenting())));
        salary.setSerious(Double.valueOf(String.format("%.2f", user.getSerious())));
        salary.setSalary(Double.valueOf(String.format("%.2f", total)));
        salary.setLate(Double.valueOf(String.format("%.2f", maps.get("late"))));
        salary.setEarly(Double.valueOf(String.format("%.2f", maps.get("early"))));
        salary.setAbsenteeism(Double.valueOf(String.format("%.2f", maps.get("absenteeism"))));
        salary.setUserid(user.getUserid());
        salary.setTime(map.get("start").toString().substring(0, 7) + "-15 00:00:00");
        salary.setEat(Double.valueOf(String.format("%.2f", eat)));
        return staffMap.addSalary(salary);
    }


    /**
     * 计算出她的加班工资
     *
     * @param map
     * @param rowss
     * @return
     */
    public double over(Map<String, Object> map, double rowss, Salary salary) {
        // 从数据库获取他的加班时间
        List<UtilModel> overTime = staffMap.getTime(map);
        //加班工资
        double bis = 0;
        int a = 0;
        for (UtilModel utilModel : overTime) {
            String data = HttpUtil.doGet("http://tool.bitefu.net/jiari/?d=" + utilModel.getCode());
            System.out.println("日期类型：" + data);
            switch (data) {
                case "0":
                case "1":
                    bis += Double.valueOf(utilModel.getData()) * rowss * 1.5;
                    break;
                case "2":
                    bis += Double.valueOf(utilModel.getData()) * rowss * 3.0;
                    break;
                default:
                    try {
                        throw new Exception("类型错误");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }

        List<TiaoXiu> overTimes = tiaoXiuDao.sumTiaoXiu(map);
        double sum = 0;
        for (TiaoXiu tiaoXiu : overTimes) {
            String data = HttpUtil.doGet("http://tool.bitefu.net/jiari/?d=" + tiaoXiu.getStartTime().substring(0, 10));
            double size = tiaoXiu.getSize();
            switch (data) {
                case "0":
                case "1":
                    sum += size * rowss * 1.5;
                    break;
                case "2":
                    sum += size * rowss * 3.0;
                    break;
                default:
                    System.out.println("类型错误！");
                    return 0;
            }
        }
        System.err.println("-----------------------------");
        System.err.println(bis);
        System.err.println(sum);
        System.err.println("-----------------------------");
        salary.setOvertime(Double.valueOf(String.format("%.2f", bis)));
        return Double.valueOf(String.format("%.2f", bis - sum));
    }


    /**
     * 计算需要扣除养老保险的方法
     * 养老保险：单位每个月为你缴纳21%,你自己缴纳8%;
     * 计算每个月需要扣除医疗保险的方法
     * 医疗保险：单位每个月为你缴纳9%,你自己缴纳2%外加10块钱的大病统筹(大病统筹主要管住院这块）；
     * 计算每个月需要扣除的失业保险的方法
     * 失业保险：单位每个月为你缴纳2%,你自己缴纳1%;
     * 计算每个月需要扣除公积金数额的方法
     * 住房公积金：单位每个月为你缴纳8%,你自己缴纳8%
     *
     * @param society
     * @return
     */
    public double getwuXian(double society, Salary salary) {
        double yiLiao = society * 0.02;
        double yangLao = society * 0.08;
        double shiYe = society * 0.05;
        double zhufang = society * 0.05;

        //社保基数：3017.01
        // 公积金基数: 1380


        //个人
        salary.setMedicalTreatment(Double.valueOf(String.format("%.2f", yiLiao)));
        salary.setProvideForTheAged(Double.valueOf(String.format("%.2f", yangLao)));
        salary.setOccupationalInjury(Double.valueOf(String.format("%.2f", shiYe)));
        salary.setHousing(Double.valueOf(String.format("%.2f", zhufang)));

        //公司五险
        salary.setMedcalCompany(Double.valueOf(String.format("%.2f", society * 0.072)));
        salary.setPensionCompany(Double.valueOf(String.format("%.2f", society * 0.16)));
        salary.setUnemploymentCompany(Double.valueOf(String.format("%.2f", society * 0.05)));
        salary.setIndustriakCompany(Double.valueOf(String.format("%.2f", society * 0.0045)));
        salary.setHousingCompany(Double.valueOf(String.format("%.2f", society * 0.05)));
        return zhufang + shiYe + yiLiao + yangLao;
    }


    public double getzhuanxiang(double children, double supportTheOld, double adult, double serious, double renting, double draw) {
        return children + supportTheOld + adult + serious + renting + draw;
    }

    /**
     * 计算应扣税额
     *
     * @param total
     * @return
     */
    public double getTax(double total) {
        double money = total - 5000;

        if (money <= 0) {
            return 0;
        }

        if (money < 1500) {
            return money * 0.03;
        }

        if (money < 4500) {
            return money * 0.1 - 105;
        }

        if (money < 9000) {
            return money * 0.2 - 555;
        }

        if (money < 35000) {
            return money * 0.25 - 1005;
        }

        if (money < 55000) {
            return money * 0.3 - 2755;
        }

        if (money < 80000) {
            return money * 0.35 - 5505;
        }

        return money * 0.45 - 13505;
    }
}