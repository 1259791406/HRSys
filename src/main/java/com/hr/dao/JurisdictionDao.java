package com.hr.dao;

import com.hr.Overall.UtilModel;
import com.hr.model.Jurisdiction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//权限
@Mapper
public interface JurisdictionDao {

    @Select("SELECT * FROM View_jur WHERE id = #{id}")
    List<Jurisdiction> childs(long id);

    @Select("SELECT id AS code,name AS data FROM View_jur WHERE id = #{id}")
    UtilModel GetData(String id);

    @Select("SELECT id AS code,name AS data FROM Jurisdiction WHERE paratid != 0")
    List<UtilModel> list();
}