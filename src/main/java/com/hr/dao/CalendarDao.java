package com.hr.dao;

import com.hr.Overall.UtilModel;
import com.hr.model.CalendarList;
import com.hr.model.Scheduling;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface CalendarDao {

    /**
     * 分页查询
     *
     * @param calendarList 分页查询排班列表
     * @return
     */
    @Select("<script>" +
            "SELECT b.* FROM (SELECT ROW_NUMBER() OVER(ORDER BY a.id) num,a.* FROM View_CalendarList  a WHERE 1 = 1 " +
            "<if test = 'userId != null '> and userId = #{userId} </if>" +
            ") b  WHERE b.num  BETWEEN #{page} AND #{limit}" +
            "</script>")
    List<CalendarList> page(CalendarList calendarList);

    /**
     * 统计数量
     *
     * @param calendarList 统计查询得总计数量
     * @return
     */
    @Select("<script>" +
            "SELECT COUNT(id) FROM View_CalendarList WHERE 1 = 1 " +
            "<if test = 'userId != null '> and userId = #{userId} </if>" +
            "</script>")
    int count(CalendarList calendarList);

    @Select("<script>" +
            "SELECT * FROM CalendarList_cn WHERE 1 = 1 " +
            "<if test = 'start != null '> <if test = 'userId != 0 '> and userId = #{userId} </if> </if>" +
            "<if test = 'start != null '> and start > #{start} </if>" +
            " order by start </script>")
    List<CalendarList> list(CalendarList calendarList);

    @Select("<script>" +
            "SELECT COUNT(id) FROM CalendarList_cn WHERE 1 = 1 " +
            "<if test = 'userId != null '> <if test = 'userId != 0 '> and userId = #{userId} </if> </if>" +
            "<if test = 'start != null '> and start > #{start} </if>" +
            "</script>")
    int size(CalendarList calendarList);

    @Select("EXECUTE GetDateList #{time}")
    List<String> GetDateList(String time);

    @Select("EXECUTE GetSchedulingList #{time}")
    List<Scheduling> GetSchedulingList(String time);

    /**
     * 增加一条排班记录
     *
     * @param calendarList
     * @return
     */
    @Insert("INSERT INTO CalendarList (userId,title,start,endTime,state,marke) VALUES (#{userId},#{title},#{start},#{endTime},#{state},#{marke})")
    int AddCaData(CalendarList calendarList);

    /**
     * 修改一条排班记录
     *
     * @param calendarList 排班实体类
     * @return
     */
    @Update("UPDATE CalendarList SET endTime = #{endTime} ,marke = #{marke} WHERE id = #{id}")
    int UpdateData(CalendarList calendarList);

    /**
     * 替班终审同意结束。修改一条排班记录
     */
    @Update("UPDATE calendarList SET state = 4 ,userId = #{userId} ,title = #{userId} ,Reserve6 = #{reserve6} WHERE id = #{id}")
    int UpdateSub(CalendarList calendarList);

    /**
     * 根据userid 获取未来一周上班得
     *
     * @param userid
     * @return
     */
    @Select("SELECT CONVERT(VARCHAR(10),start,20) AS code,CONVERT(VARCHAR(20),start,20) AS data,CONVERT(VARCHAR(20),endTime,20) AS mes FROM calendarList WHERE userid = #{userid} AND start < DATEADD(day, 7, GETDATE()) AND start > GETDATE()")
    List<UtilModel> GetWeekUser(String userid);

    /**
     * 查看今天是否有排班记录
     * @param userid
     * @return
     */
    @Select("SELECT COUNT(id) FROM calendarList WHERE userid = #{userid} AND CONVERT(VARCHAR(10),start,23) = CONVERT(VARCHAR(10),GETDATE(),23)")
    int LookDayIsCa(String userid);

    /**
     * 获取某天下班时间
     *
     * @param userid
     * @return
     */
    @Select("SELECT TOP 1 CONVERT(VARCHAR(20),endTime,20) AS endTime FROM calendarList WHERE userid = #{userid} AND CONVERT(VARCHAR(10),start,23) = CONVERT(VARCHAR(10),GETDATE(),23) ORDER BY id DESC")
    String GetEndTime(String userid);
}