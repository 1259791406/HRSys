package com.hr.dao;


import com.hr.Overall.UtilModel;
import com.hr.model.TiaoXiu;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface TiaoXiuDao {


    @Select("SELECT * FROM tiaoXiu where state = 2 and  userid=#{userid} and  startTime >= #{start} and startTime <= #{end}")
    List<TiaoXiu> sumTiaoXiu(Map<String,Object> map);

    @Select("<script>" +
            "SELECT b.* FROM (SELECT ROW_NUMBER() OVER(ORDER BY a.id) num,a.* FROM View_TiaoXiu  a WHERE 1 = 1 " +
            "<if test = 'username != null '> and username = #{username} </if>" +
            "<if test = 'userid != null '> and userid = #{userid} </if>" +
            "<if test = 'app != null '> and app = #{app} </if>" +
            ") b  WHERE b.num  BETWEEN #{page} AND #{limit}" +
            "</script>")
    List<TiaoXiu> list(TiaoXiu tiaoXiu);

    @Select("<script>" +
            "SELECT COUNT(id) FROM View_TiaoXiu WHERE 1 = 1 " +
            "<if test = 'username != null '> and username = #{username} </if>" +
            "<if test = 'userid != null '> and userid = #{userid} </if>" +
            "<if test = 'app != null '> and app = #{app} </if>" +
            "</script>")
    int count(TiaoXiu tiaoXiu);

    @Insert("INSERT INTO tiaoXiu(userid, code, startTime, size, APP, make) VALUES  (#{userid},#{code},#{startTime},#{size},#{app},#{make})")
    int Add(TiaoXiu tiaoXiu);

    @Update("UPDATE tiaoXiu SET state = 2 WHERE code = #{code}")
    int Yes(String code);

    @Update("UPDATE tiaoXiu SET state = 3 WHERE code = #{code}")
    int No(String code);

    @Update("UPDATE tiaoXiu SET App = #{data} WHERE code = #{code}")
    int update(UtilModel utilModel);

    @Select("SELECT App FROM tiaoXiu WHERE code = #{code}")
    String GetApp(String code);

    @Select("SELECT startTime AS code,DATEDIFF(hh,startTime,endTime) AS data FROM Overtime WHERE userid = #{userid} AND state = 2 AND DATEDIFF(month,startTime,GETDATE()) = 0")
    List<UtilModel> dateList(String userid);
}
