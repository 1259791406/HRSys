package com.hr.dao;

import com.hr.model.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogDao {

    @Insert("INSERT INTO log(ip,url,data,type,code) VALUES(#{ip},#{url},#{data},#{type},#{code})")
    int Add(Log log);
}
