package com.hr.dao;

import com.hr.Overall.UtilModel;
import com.hr.model.Approval;
import com.hr.model.OverType;
import com.hr.model.Overtime;
import com.hr.model.PageUtil;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OvertimeDao {

    /**
     * 所有加班申请类型数据
     *
     * @return
     */
    @Select("SELECT id,name FROM OverType")
    List<OverType> list();

    @Select("<script>" +
            "SELECT b.* FROM (SELECT ROW_NUMBER() OVER(ORDER BY a.id) num,a.* FROM view_over a WHERE 1 = 1 " +
            "<if test = 'userid != null'> and userid = #{userid} </if>" +
            "<if test = 'type != null'> and type = #{type} </if>" +
            "<if test = 'Approval != null'> and Approval = #{Approval} </if>" +
            "<if test = 'state != null'> and state = #{state} </if>" +
            ") b  WHERE b.num  BETWEEN #{page} AND #{limit}" +
            "</script>")
    List<Overtime> page(Overtime overtime);

    @Select("<script>" +
            "SELECT COUNT(id) FROM view_over WHERE 1 = 1 " +
            "<if test = 'type != null'> and type = #{type} </if>" +
            "<if test = 'userid != null'> and userid = #{userid} </if>" +
            "<if test = 'Approval != null'> and Approval = #{Approval} </if>" +
            "<if test = 'state != null'> and state = #{state} </if>" +
            "</script>")
    int count(Overtime overtime);

    @Select("<script>" +
            "SELECT b.* FROM (SELECT ROW_NUMBER() OVER(ORDER BY a.id) num,a.* FROM View_over_cn  a WHERE 1 = 1 " +
            "<if test = 'userid != null'> and userid = #{userid} </if>" +
            ") b  WHERE b.num  BETWEEN #{page} AND #{limit}" +
            "</script>")
    List<Overtime> h5list(Overtime overtime);

    @Select("<script>" +
            "SELECT COUNT(id) FROM View_over_cn WHERE 1 = 1 " +
            "<if test = 'userid != null'> and userid = #{userid} </if>" +
            "</script>")
    int size(Overtime overtime);

    /**
     * 增加一条加班申请表记录
     *
     * @param overtime
     * @return
     */
    @Insert("INSERT INTO Overtime (userid, code,startTime, endTime, type, marke,Approval) VALUES (#{userid}, #{code}, #{startTime}, #{endTime}, #{type}, #{marke},#{Approval})")
    int AddData(Overtime overtime);

    /**
     * 查看一条加班申请信息
     *
     * @param code
     * @return
     */
    @Select("SELECT * FROM View_over WHERE code = #{code}")
    Overtime GetOne(String code);

    /**
     * 查看加班申请单据是否正在审批中
     *
     * @param code 单据code
     * @return
     */
    @Select("SELECT COUNT(id) FROM Overtime WHERE state = 1 AND code = #{code}")
    int AppSize(String code);

    /**
     * 加班申请单审批结束，最终结果为同意时，修改单据状态
     *
     * @param code 单据code
     * @return
     */
    @Update("UPDATE Overtime SET state = 2 WHERE code = #{code}")
    int YesOver(String code);

    /**
     * 加班申请单审批结束，最终结果为不同意时，修改单据状态
     *
     * @param code 单据code
     * @return
     */
    @Update("UPDATE Overtime SET state = 3 WHERE code = #{code}")
    int NoOver(String code);

    /**
     * 修改当前审批人为谁
     *
     * @param utilModel
     * @return
     */
    @Update("UPDATE Overtime SET Approval = #{data} WHERE code = #{code}")
    int UpdateApp(UtilModel utilModel);
}