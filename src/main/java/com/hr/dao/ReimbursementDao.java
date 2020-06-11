package com.hr.dao;

import com.hr.Overall.UtilModel;
import com.hr.model.ReimDetail;
import com.hr.model.Reimbursement;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface ReimbursementDao {

    @Select("<script>" +
            "SELECT b.* FROM (SELECT ROW_NUMBER() OVER(ORDER BY a.id) num,a.* FROM View_reim  a WHERE 1 = 1 " +
            "<if test = 'username != null '> and username = #{username} </if>" +
            "<if test = 'app != null '> and app = #{app} </if>" +
            "<if test = 'type != null '> and type = #{type} </if>" +
            ") b  WHERE b.num  BETWEEN #{page} AND #{limit}" +
            "</script>")
    List<Reimbursement> list(Reimbursement reimbursement);

    @Select("<script>" +
            "SELECT COUNT(id) FROM View_reim WHERE 1 = 1 " +
            "<if test = 'username != null '> and username = #{username} </if>" +
            "<if test = 'app != null '> and app = #{app} </if>" +
            "<if test = 'type != null '> and type = #{type} </if>" +
            "</script>")
    int count(Reimbursement reimbursement);

    @Insert("INSERT INTO ReimDetail(code, marke, type, startTime, endTime, subjectCode, cost, Taxes, price) VALUES (#{code},#{marke},#{type},#{startTime},#{endTime},#{subjectCode},#{cost},#{taxes},#{price})")
    int AddDe(ReimDetail reimDetail);

    /**
     * 加班申请单审批结束，最终结果为同意时，修改单据状态
     *
     * @param code 单据code
     * @return
     */
    @Update("UPDATE Reimbursement SET state = 2 WHERE code = #{code}")
    int Yes(String code);

    /**
     * 加班申请单审批结束，最终结果为不同意时，修改单据状态
     *
     * @param code 单据code
     * @return
     */
    @Update("UPDATE Reimbursement SET state = 3 WHERE code = #{code}")
    int No(String code);

    @Insert("INSERT INTO Reimbursement (code,userid,type,price,App,marke) VALUES(#{code},#{userid},#{type},#{price},#{app},#{marke})")
    int Add(Reimbursement reimbursement);

    @Update("UPDATE Reimbursement SET App = #{data} WHERE code = #{code}")
    int update(UtilModel utilModel);

    @Select("SELECT code AS code,name AS data FROM ReimType")
    List<UtilModel> model();

    @Select("SELECT App FROM Reimbursement WHERE code = #{code}")
    String GetApp(String code);

    @Select("SELECT TOP 1 orderNum FROM TaskList WHERE code = #{code} ORDER BY id ASC")
    long GetOrder(String code);

    @Select("SELECT * FROM View_detil WHERE code = #{code}")
    List<ReimDetail> reList(String code);
}
