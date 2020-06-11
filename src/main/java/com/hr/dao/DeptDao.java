package com.hr.dao;

import com.hr.model.Tree;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeptDao {

    @Select("<script>" +
            "SELECT id AS value,name AS title FROM hn_dept  a WHERE 1 = 1 AND paratid != 0 " +
            "<if test = 'title != null '> and name = #{title}</if>" +
            "</script>")
    List<Tree> DeList(Tree tree);

    @Select("<script>" +
            "SELECT COUNT(id) FROM hn_dept WHERE 1 = 1 " +
            "<if test = 'title != null '> <if title = 'title != 0 '> and name = #{title} </if> </if>" +
            "</script>")
    int count(Tree tree);

    @Select("SELECT id AS value,name AS title FROM hn_dept")
    List<Tree> list();

    @Insert("INSERT INTO hn_dept (id,name,paratid) VALUES(#{time},#{title},#{value})")
    int Add(Tree tree);
}
