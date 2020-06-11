package com.hr.dao;

import com.hr.model.ToolModal;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface FileDao {
    @Insert("INSERT INTO allFile (fileOldName,fileNewName,fileSize,fileType,uuid) VALUES (#{oldName},#{newName},#{size},#{type},#{uuid})")
    int AddFile(Map<String, Object> map);

    @Select("SELECT fileOldName AS name,fileNewName AS marke,fileSize AS code,id AS id FROM allFile WHERE state = 1 AND uuid = #{uuid}")
    List<ToolModal> list(String uuid);

    @Update("UPDATE allFile SET state = 2 WHERE id = #{id}")
    int delete(String id);
}