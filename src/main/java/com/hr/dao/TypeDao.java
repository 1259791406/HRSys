package com.hr.dao;

import com.hr.model.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TypeDao {
    /**
     * 获取所有类型
     *
     * @return
     */
    @Select("")
    List<Type> list();

    /**
     * 获取所有类型的条数
     *
     * @return
     */
    @Select("")
    int GetSize();
}
