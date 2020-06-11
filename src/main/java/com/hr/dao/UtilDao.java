package com.hr.dao;

import com.hr.model.ToolModal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UtilDao {

    @Select("SELECT url FROM url WHERE state = 1")
    List<String> UrlList();

    /**
     * 查询所有的学历列表
     *
     * @return
     */
    @Select("SELECT id AS code,name AS name FROM Education")
    List<ToolModal> EducationList();

    @Select("SELECT TOP 1 orderNum FROM TaskList WHERE code = #{code} ORDER BY id ASC")
    long GetOrder(String code);
}
