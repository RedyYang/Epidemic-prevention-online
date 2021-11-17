package com.yang.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yang.entity.PageResult;
import com.yang.entity.QueryPageBean;
import com.yang.pojo.CheckGroup;
import com.yang.pojo.CheckItem;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface CheckGroupService {
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    PageResult pageQuery(QueryPageBean queryPageBean);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    List<CheckGroup> findAll();
}
