package com.hr.dao;

import com.hr.Overall.UtilModel;
import com.hr.model.CalendarList;
import com.hr.model.Substitute;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SubstituteDao {

    @Select("<script>" +
            "SELECT b.* FROM (SELECT ROW_NUMBER() OVER(ORDER BY a.id) num,a.* FROM View_Sub  a WHERE 1 = 1 " +
            "<if test = 'startUserid != null '> and startUserid like '%${startUserid}%' </if>" +
            "<if test = 'endUserid != null '> and endUserid like '%${endUserid}%' </if>" +
            "<if test = 'str2 != null '> and str2 = #{str2} </if>" +
            ") b  WHERE b.num  BETWEEN #{page} AND #{limit}" +
            "</script>")
    List<Substitute> list(Substitute substitute);

    @Select("<script>" +
            "SELECT COUNT(id) FROM View_Sub WHERE 1 = 1 " +
            "<if test = 'startUserid != null '> and startUserid like '%${startUserid}%' </if>" +
            "<if test = 'endUserid != null '> and endUserid like '%${endUserid}%' </if>" +
            "<if test = 'str2 != null '> and str2 = #{str2} </if>" +
            "</script>")
    int count(Substitute substitute);

    /**
     * 添加一条替班信息
     *
     * @param substitute
     * @return
     */
    @Insert("INSERT INTO Substitute (table_id,startUserid,endUserid,str1) VALUES(#{tableId},#{startUserid},#{endUserid},#{str1})")
    int AddData(Substitute substitute);

    /**
     * 终止某个替班申请单申请
     *
     * @param code
     * @return
     */
    @Update("UPDATE Substitute SET state = 3 WHERE table_id = #{code}")
    int ApprovalNo(String code);

    /**
     * 同意某个替班申请单申请
     *
     * @param code
     * @return
     */
    @Update("UPDATE Substitute SET state = 2 WHERE table_id = #{code}")
    int ApprovalYes(String code);

    /**
     * 修改单据当前审批人
     *
     * @param utilModel
     * @return
     */
    @Update("UPDATE Substitute SET str2 = #{code} WHERE table_id = #{data}")
    int UpdateApp(UtilModel utilModel);

    /**
     * 查看加班申请单据是否正在审批中
     *
     * @param code 单据code
     * @return
     */
    @Select("SELECT COUNT(id) FROM Substitute WHERE state = 1 AND table_id = #{code}")
    int AppSize(String code);

    /**
     * 查询某一张替班申请记录
     *
     * @param code
     * @return
     */
    @Select("SELECT * FROM View_Sub_cn WHERE table_id = #{code}")
    Substitute GetOne(String code);

    /**
     * 获取申请替班人得未来XX天得所有工作记录
     *
     * @param userid 用户ID
     * @return
     */
    @Select("EXECUTE GetSubCaList #{userid}")
    List<String> GetSubCaList(String userid);
}
