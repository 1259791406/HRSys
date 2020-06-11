package com.hr.dao;

import com.hr.Overall.UtilModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LeaveType {

    @Select("SELECT id AS code,name AS data FROM leaveType")
    List<UtilModel> list();
}
