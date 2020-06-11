package com.hr.dao;

import com.hr.Overall.UtilModel;
import com.hr.model.Approval;
import com.hr.model.TaskList;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TaskListDao {

    /**
     * 增加审批流程信息
     *
     * @param taskList
     * @return
     */
    @Insert("INSERT INTO TaskList (code,userid,orderNum,type) VALUES (#{code}, #{userid}, #{orderNum},#{type})")
    int AddData(TaskList taskList);

    /**
     * 单据审批修改审批表信息
     *
     * @param taskList
     * @return
     */
    @Update("UPDATE TaskList SET opinion = #{opinion}, state = #{state}, time = #{time} ,marke = #{marke} WHERE code = #{code} AND orderNum = #{orderNum}")
    int UpdateTask(TaskList taskList);

    /**
     * 查看当前加班申请单得第一审批人
     *
     * @param code 单据code
     * @return
     */
    @Select("SELECT TOP 1 * FROM TaskList WHERE flag = 1 AND state = 1 AND code = #{code} ORDER BY orderNum")
    TaskList GetOne(String code);

    /**
     * 统计某单据还有多少人未审批
     *
     * @param code
     * @return
     */
    @Select("SELECT COUNT(id) FROM TaskList WHERE state = 1 AND code = #{code}")
    int GetSize(String code);

    /**
     * 终止某个单据得审批流程表信息
     *
     * @param approval
     * @return
     */
    @Update("EXECUTE RefuseTask #{code},#{orderNum}")
    int RefuseTask(Approval approval);

    /**
     * 获取所有需要 userid 审批得信息
     *
     * @param userid
     * @return
     */
    @Select("SELECT code FROM TaskList WHERE state = 1 AND userid = #{userid} GROUP BY code")
    List<String> GetTaskUserList(String userid);

    /**
     * 获取某张单据当前审批人为谁
     *
     * @param code
     * @return
     */
    @Select("SELECT TOP 1 userId AS code,orderNum AS data FROM TaskList WHERE time IS NULL AND flag = 1 AND code = #{code}")
    UtilModel GetUserid(String code);

    /**
     * 查看某个单据的所有审批过程！
     *
     * @param code 单据code
     * @return
     */
    @Select("SELECT id,code,CASE userid WHEN '' THEN '' ELSE (SELECT username FROM hnuser WHERE userid = t.userid) END AS userid,CASE state WHEN 1 THEN '未审批' WHEN 2 THEN '同意' WHEN 3 THEN '拒绝' ELSE '异常' END AS state,opinion,orderNum,CONVERT(VARCHAR(20),time,20) AS time,marke,flag,CONVERT(VARCHAR(20),addTime,20) AS addTime,type FROM TaskList t WHERE code = #{code}")
    List<TaskList> GetTableList(String code);
}