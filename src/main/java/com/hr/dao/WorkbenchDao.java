package com.hr.dao;

import com.hr.model.Workbench;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface WorkbenchDao {

    @Select("<script>" +
            "SELECT b.* FROM (SELECT ROW_NUMBER() OVER(ORDER BY a.id) num,a.* FROM View_Work  a WHERE 1 = 1 AND ThreadLength = #{ThreadLength}" +
            "<if test = 'username != null '> and username = #{username} </if>" +
            ") b  WHERE b.num  BETWEEN #{page} AND #{limit}" +
            "</script>")
    List<Workbench> list(Workbench workbench);

    @Select("<script>" +
            "SELECT COUNT(id) FROM View_Work WHERE 1 = 1 AND ThreadLength = #{ThreadLength}" +
            "<if test = 'username != null '> and username = #{username} </if>" +
            "</script>")
    int count(Workbench workbench);

    @Insert("INSERT INTO workLog(userid, GoTime, OutTime, ZGoTime, ZOutTime, errorCode, chidao, zaotui, ji, shiji, str1, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, str13, str14, str15, str16, str17, str18, str19, str20, app, code, marke) VALUES (" +
            "#{userid}, #{goTime}, #{outTime}, #{zGoTime}, #{zOutTime}, #{errorCode}, #{chidao}, #{zaotui}, #{ji}, #{shiji}, #{str1}, #{str2}, #{str3}, #{str4}, #{str5}, #{str6}, #{str7}, #{str8}, #{str9}, #{str10}, #{str11}, #{str12}, #{str13}, #{str14}, #{str15}, #{str16}, #{str17}, #{str18}, #{str19}, #{str20}, #{app}, #{code}, #{marke})")
    int add(Workbench workbench);

    @Update("UPDATE workLog SET state = 2 WHERE id = #{id}")
    int update(Workbench workbench);
}