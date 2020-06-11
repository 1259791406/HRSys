package com.hr.dao;

import com.hr.Overall.UtilModel;
import com.hr.model.Approval;
import com.hr.model.ToolModal;
import com.hr.model.Visitor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface VisitorDao {

    /**
     * 添加一张访客申请表
     *
     * @param visitor
     * @return
     */
    @Insert("INSERT INTO Visitor (table_id,user_id,goTime,outTime,visitName,visitCompany,visitPhone,visitNum,visitType,marke,f_id,Approval,ReceptionStaff,idCard,region,IsWorkingMeal,CarType,CardType) " +
            "VALUES (#{tableId}, #{userId},#{goTime},DATEADD(hh, ${outTime}, #{goTime}),#{visitName},#{visitCompany},#{visitPhone},#{visitNum},#{visitType},#{marke},#{fId},#{Approval},#{ReceptionStaff},#{idCard},#{region},#{IsWorkingMeal},#{CarType},#{CardType})")
    int AddData(Visitor visitor);

    @Select("<script>" +
            "SELECT b.* FROM (SELECT ROW_NUMBER() OVER(ORDER BY a.id) num,a.* FROM View_Vistor  a WHERE 1 = 1 " +
            "<if test = 'visitName != null'> and visitName like '%${visitName}%' </if>" +
            "<if test = 'userId != null'> and userId = #{userId} </if>" +
            "<if test = 'tableId != null'> and tableid = #{tableId} </if>" +
            "<if test = 'Approval != null'> and Approval = #{Approval} </if>" +
            "<if test = 'visitType != null'> and visitType like '%${visitType}%' </if>" +
            ") b  WHERE b.num  BETWEEN #{page} AND #{limit}" +
            "</script>")
    List<Visitor> Page(Visitor visitor);

    @Select("<script>" +
            "SELECT COUNT(id) FROM View_Vistor WHERE 1 = 1 " +
            "<if test = 'visitName != null'> and visitName like '%${visitName}%' </if>" +
            "<if test = 'userId != null'> and userId = #{userId} </if>" +
            "<if test = 'tableId != null'> and tableid = #{tableId} </if>" +
            "<if test = 'Approval != null'> and Approval = #{Approval} </if>" +
            "<if test = 'visitType != null'> and visitType like '%${visitType}%' </if>" +
            "</script>")
    int count(Visitor visitor);

    /**
     * 所有来访类型表
     *
     * @return
     */
    @Select("SELECT id AS id, name AS name,CONVERT(VARCHAR(100),time, 20) AS time FROM visitType")
    List<ToolModal> Type();

    /**
     * 审批访客申请单
     *
     * @param approval
     * @return
     */
    @Update("UPDATE Visitor SET state = #{state} WHERE table_id = #{code}")
    int Approval(Approval approval);

    /**
     * 安保部门点击进入触发修改时间
     *
     * @param id
     * @return
     */
    @Update("UPDATE Visitor SET sureGoTime = GETDATE() WHERE table_id = #{id}")
    int UpdateSureGo(String id);

    /**
     * 安保部门点击离开触发修改时间
     *
     * @param id
     * @return
     */
    @Update("UPDATE Visitor SET sureOutTime = GETDATE() WHERE table_id = #{id}")
    int UpdateSureOut(String id);

    @Select("SELECT * FROM View_Vistor WHERE tableid = #{id}")
    Visitor GetOne(String id);

    @Update("UPDATE Visitor SET Approval = #{data} WHERE table_id = #{code}")
    int UpdateApp(UtilModel utilModel);

    @Update("UPDATE Visitor SET state = 2 WHERE table_id = #{code}")
    int YesApp(String code);

    @Update("UPDATE Visitor SET state = 3 WHERE table_id = #{code}")
    int NoApp(String code);

    @Select("SELECT Approval FROM Visitor WHERE table_id = #{code}")
    String GetApp(String code);
}