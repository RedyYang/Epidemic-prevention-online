package com.yang.dao;

import com.github.pagehelper.Page;
import com.yang.pojo.CheckGroup;

import java.util.HashMap;
import java.util.List;

public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void addCheckGroupAndCheckitem(HashMap<String, Integer> map);

    Page<CheckGroup> findByCondition(String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void edit(CheckGroup checkGroup);

    void deleteAssociation(Integer id);

    List<CheckGroup> findAll();
}
