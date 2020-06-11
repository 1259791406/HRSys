package com.hr.dao;

import com.hr.model.Task;
import com.hr.model.ToolModal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskDao {

    /**
     * 查询该单据所经历得审批流程
     *
     * @param toolModal 工具类
     *                  userid ：申请人ID
     *                  code ：单据（table_id）code
     * @return
     */
    @Select("EXECUTE GetTaskList #{userid},#{code}")
    List<Task> list(ToolModal toolModal);

    /**
     * 通过userid 和 单据id 来查询所需要得审批过程！
     *
     * @param toolModal 工具类
     *                  userid ：申请人ID
     *                  code ： 单据I（table_id） code
     * @return
     */
    @Select("SELECT * FROM Task WHERE code = (SELECT task_code FROM BillTask WHERE table_id = #{code} AND depe_id = (SELECT deptid FROM hnuser WHERE userId = #{userid} )) ORDER BY id ASC")
    List<Task> TaskList(ToolModal toolModal);
}
