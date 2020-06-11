package com.hr.dao;

import java.util.List;
import java.util.Map;

import com.hr.Overall.UtilModel;
import com.hr.model.CalendarList;
import com.hr.model.Salary;
import com.hr.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

/**
 * 处理员工工资的持久层接口
 *
 * @author Administrator
 */
@Mapper
public interface StaffDao {


    @Select("SELECT * FROM View_salary WHERE userid = #{userid} and time >= #{start} and time <= #{end}")
    /**
     * 通过userid  和开始结束的时间去查询他的工资明细
     */
    List<Salary> getSalary(Map<String, Object> map);

    @Select("SELECT * FROM View_salary WHERE time >= #{start} and time <= #{end} ${data}")
    List<Salary> pageList(Map<String, Object> map);

    @Select("SELECT  *  FROM View_CalendarList WHERE user_id=#{userid} and  start >= #{start} and endtime <= #{end}")
    /**
     * 查询应上班时间
     */
    List<CalendarList> getFive(Map<String, Object> map);


    @Select("SELECT * FROM hnuser WHERE userid = #{userid}")
    /**
     * 通过userid 去查询用户表的全部信息
     */
    User getUser(String userid);

    @Select("SELECT SUBSTRING(startTime, 0, 11) AS code,ISNULL(DATEDIFF(hour,startTime, endtime),0) AS data FROM Overtime WHERE state = 2 AND userid = #{userid} and startTime >= #{start} and endTime <= #{end}")
    /**
     * 通过id去数据查询他的加班时长
     */
    List<UtilModel> getTime(Map<String, Object> map);

    @Select("SELECT startTime AS code,endTime AS data FROM Overtime WHERE state = 2 AND userid = #{userid} and startTime >= #{start} and endTime <= #{end}")
    List<UtilModel> GetTimeS(Map<String,Object> map);

    @Insert("INSERT INTO salary (" +
            "time,basic,hour,night,month_performance,quarter_performance,year_end_bonus,overtime,allowance,particularly_aawards,festival,travel,provide_for_the_aged,medical_treatment,housing,in_dry_dock,occupational_injury,Pension_Company,Medcal_Company,Unemployment_Company,Birth_Company,Industriak_Company,Housing_Company,Children,SupportTheOld,Adult,Draw,Renting,Serious,salary,leave,late,early,absenteeism,Overtime_pay,userid,app,code,eat " +
            ") VALUES (" +
            "#{time}, #{basic},#{hour},#{night},#{monthPerformance},#{quarterPerformance},#{yearEndBonus},#{overtime},#{allowance},#{particularlyAawards},#{festival},#{travel},#{provideForTheAged},#{medicalTreatment},#{housing},#{inDryDock},#{occupationalInjury},#{pensionCompany},#{medcalCompany},#{unemploymentCompany},#{birthCompany},#{industriakCompany},#{housingCompany},#{children},#{supportTheOld},#{adult},#{draw},#{renting},#{serious},#{salary},#{leave},#{late},#{early},#{absenteeism},#{overtime_Pay},#{userid},#{app},#{code},#{eat})")
    /**
     * 数据库插入操作
     */
    Integer addSalary(Salary salary);

    // 获取他的请假时长
    @Select("SELECT SUM(Duration) FROM leave WHERE state = 2 and userid=#{userid} and time >= #{start} and time <= #{end}")
    /**
     *
     */
    Double getDuration(Map<String, Object> map);

    /**
     * 所有的排班总时长
     *
     * @param userid
     * @return
     */
    @Select("SELECT ISNULL(SUM(DATEDIFF(hour,start, endtime)),0) FROM calendarList WHERE userid = #{userid}")
    String countTime(String userid);

    @Delete("DELETE FROM salary WHERE userid=#{userid} and time >= #{start}  and time <=  #{end}")
    Integer delete(Map<String, Object> map);

    @Update("UPDATE salary SET App = #{app} WHERE code  = #{code}")
    int App(Map<String, Object> map);

    @Update("UPDATE salary SET state = 2 WHERE code  = #{code}")
    int YesApp(String code);

    @Update("UPDATE salary SET state = 3 WHERE code  = #{code}")
    int noApp(String code);

    @Select("<script>" +
            "SELECT b.* FROM (SELECT ROW_NUMBER() OVER(ORDER BY a.id) num,a.* FROM View_salary_cn  a WHERE 1 = 1 " +
            "<if test = 'userid != null '> and userid = #{userid} </if>" +
            "<if test = 'app != null '> and App = #{app} </if>" +
            "<if test = 'time != null '> and time &gt;= #{time} and time &lt;= getdate() </if>" +
            "<if test = 'id != 0 '> and id = #{id} </if>" +
            ") b  WHERE b.num  BETWEEN #{page} AND #{limit}" +
            "</script>")
    List<Salary> list(Salary salary);

    @Select("<script>" +
            "SELECT COUNT(id) FROM View_salary_cn WHERE 1 = 1 " +
            "<if test = 'userid != null '> and userid = #{userid} </if>" +
            "<if test = 'app != null '> and App = #{app} </if>" +
            "<if test = 'time != null '> and time &gt;= #{time} and time &lt;= getdate() </if>" +
            "<if test = 'id != null '> and id = #{id} </if>" +
            "</script>")
    int size(Salary salary);


}