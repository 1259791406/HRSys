package com.hr.dao;

import com.hr.Overall.UtilModel;
import com.hr.model.Approval;
import com.hr.model.Eat;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface EatDao {

    @Insert("INSERT INTO eat( userid, username, eatTime, code, App, origin, remark) VALUES (#{userid}, #{username}, #{eatTime}, #{code}, #{app}, #{origin}, #{remark})")
    Integer addEat(Eat eat);

    @Select("<script>" +
            "SELECT b.* FROM (SELECT ROW_NUMBER() OVER(ORDER BY a.id) num,a.* FROM View_eat  a WHERE 1 = 1 " +
            "<if test = 'origin != null '> and origin = #{origin} </if>" +
            "<if test = 'username != null '> and username = #{username} </if>" +
            ") b  WHERE b.num  BETWEEN #{page} AND #{limit}" +
            "</script>")
    List<Eat> ByEat(Eat eat);

    @Select("<script>" +
            "SELECT COUNT(id) FROM View_eat WHERE 1 = 1 " +
            "<if test = 'origin != null '> and origin = #{origin} </if>" +
            "<if test = 'username != null '> and username = #{username} </if>" +
            "</script>")
    int count(Eat eat);

    /**
     * 获取当前审批人
     * @param code
     * @return
     */
    @Select("SELECT app FROM eat WHERE code = #{code}")
    String GetApp(String code);

    /**
     * 同意
     *
     * @param code
     * @return
     */
    @Update("UPDATE eat SET state = 2 WHERE code = #{code}")
    int Yes(String code);

    /**
     * 拒绝
     *
     * @param code
     * @return
     */
    @Update("UPDATE eat SET state = 3 WHERE code = #{code}")
    int No(String code);

    /**
     * 修改当前审批人
     *
     * @param utilModel
     * @return
     */
    @Update("UPDATE eat SET app = #{data} WHERE code = #{code}")
    int App(UtilModel utilModel);

    @Select("SELECT TOP 1 orderNum FROM TaskList WHERE code = #{code} ORDER BY id ASC")
    long GetOrder(String code);
}
