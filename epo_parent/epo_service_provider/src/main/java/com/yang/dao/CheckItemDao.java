package com.yang.dao;

import com.github.pagehelper.Page;
import com.yang.pojo.CheckItem;
import org.apache.ibatis.annotations.Insert;

import java.util.List;


public interface CheckItemDao {
//    @Insert("insert into t_checkitem(code,name,sex,age,price,type,remark,attention)" +
//            "values (#{code},#{name},#{sex},#{age},#{price},#{type},#{remark},#{attention})")
    void add(CheckItem checkItem);

    Page<CheckItem> selectByCondition(String queryString);

    Long findCountByCheckItemId(Integer id);

    void deleteById(Integer id);

    void edit(CheckItem checkItem);

    CheckItem findById(Integer id);

    List<CheckItem> findAll();
}
