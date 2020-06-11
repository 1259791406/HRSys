package com.hr.dao;

import com.hr.Overall.UtilModel;
import com.hr.model.LeaveModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface LeaveDao {

    /**
     * 分页查询
     *
     * @param leaveModel 分页查询排班列表
     * @return
     */
    @Select("<script>" +
            "SELECT b.* FROM (SELECT ROW_NUMBER() OVER(ORDER BY a.id) num,a.* FROM View_leave  a WHERE 1 = 1 " +
            "<if test = 'userid != null '> <if test = 'userid != 0 '> and userid = #{userid} </if> </if>" +
            "<if test = 'Approval != null '> and Approval = #{Approval} </if>" +
            ") b  WHERE b.num  BETWEEN #{page} AND #{limit}" +
            "</script>")
    List<LeaveModel> page(LeaveModel leaveModel);

    /**
     * 统计数量
     *
     * @param leaveModel 统计查询得总计数量
     * @return
     */
    @Select("<script>" +
            "SELECT COUNT(id) FROM View_leave WHERE 1 = 1 " +
            "<if test = 'userid != null '> <if test = 'userid != 0 '> and userid = #{userid} </if> </if>" +
            "<if test = 'Approval != null '> and Approval = #{Approval} </if>" +
            "</script>")
    int count(LeaveModel leaveModel);

    /**
     * 添加请假数据
     *
     * @param leaveModel
     * @return
     */
    @Insert("INSERT INTO leave (code,userid,Duration,time,str1,Approval) VALUES (#{code},#{userid},#{duration},#{time},#{str1},#{Approval})")
    int Add(LeaveModel leaveModel);

    @Update("UPDATE leave SET state = 3 WHERE code = #{code}")
    int NoApp(String code);

    @Update("UPDATE leave SET state = 2 WHERE code = #{code}")
    int YesApp(String code);

    @Update("UPDATE leave SET Approval = #{data} WHERE code = #{code}")
    int UpdateApp(UtilModel utilModel);

    /**
     * 查看某个请假单的当前审批人
     *
     * @param code
     * @return
     */
    @Select("SELECT Approval FROM leave WHERE code = #{code}")
    String isApp(String code);
}
