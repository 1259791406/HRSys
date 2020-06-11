package com.hr.dao;

import com.hr.model.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoleDao {

    @Select("SELECT * FROM View_Role WHERE ID = #{id}")
    Role GetSign(String id);

    @Select("SELECT * FROM View_Role")
    List<Role> GetList();

    @Update("UPDATE Role SET sign = #{sign},name = #{name} WHERE id = #{id}")
    int Update(Map<String,Object> map);

    @Insert("INSERT Role (name,sign) VALUES(#{name},#{sign})")
    int Add(Map<String,Object> map);
}
