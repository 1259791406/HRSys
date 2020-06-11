package com.hr.dao;

import com.hr.Overall.UtilModel;
import com.hr.model.Task;
import com.hr.model.TaskModel;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BillDao {

    /**
     * 查询所有可以审批的单据类型
     *
     * @return
     */
    @Select("SELECT code,name AS data FROM bill")
    List<UtilModel> list();

    /**
     * 根据所查询的单据类型，查询已经设置审批的部门信息
     *
     * @param code
     * @return
     */
    @Select("SELECT id AS code,name AS data FROM hn_dept WHERE id IN (SELECT depe_id FROM BillTask WHERE table_id = #{code})")
    List<UtilModel> DeptList(String code);

    /**
     * 通过部门、单据类型查询该部门该单据的审批流信息
     *
     * @param map table_id = 单据类型ID
     *            depe_id  = 部门ID
     * @return
     */
    @Select("SELECT t.*,CASE t.userid WHEN '' THEN '' ELSE (SELECT TOP 1 username FROM hnuser WHERE userid = t.userid) END AS username FROM Task t WHERE  code = (SELECT task_code FROM BillTask WHERE table_id = #{table_id} AND depe_id = #{depe_id}) ORDER BY orderNum ASC")
    List<Task> taskList(Map<String, String> map);

    /**
     * 添加审批流程示意图
     *
     * @param task
     * @return
     */
    @Insert("INSERT INTO Task (userid,code,state,orderNum) VALUES(#{userid},#{code},1,#{orderNum})")
    int Add(Task task);

    /**
     * 修改审批流程
     *
     * @param code
     * @return
     */
    @Delete("DELETE FROM Task WHERE code = #{code}")
    int Delete(String code);

    @Select("SELECT task_code FROM BillTask WHERE table_id = #{type} AND depe_id = #{dept}")
    String GetCode(TaskModel taskModel);
}