package com.yang.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yang.dao.CheckGroupDao;
import com.yang.entity.PageResult;
import com.yang.entity.QueryPageBean;
import com.yang.pojo.CheckGroup;
import com.yang.pojo.CheckItem;
import com.yang.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {


    @Autowired
    private CheckGroupDao checkGroupDao;
    @Override
    public void add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        //添加防疫组
        checkGroupDao.add(checkGroup);
        //获取自增id
        Integer checkGroupId = checkGroup.getId();
        //增加关系
        addCheckGroupAndCheckitem(checkGroupId,checkitemIds);

    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> page = checkGroupDao.findByCondition(queryString);
        long total = page.getTotal();
        List<CheckGroup> rows = page.getResult();



        return new PageResult(total,rows);
    }


    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }


    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    //编辑防疫组
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //编辑防疫组
        checkGroupDao.edit(checkGroup);

        checkGroupDao.deleteAssociation(checkGroup.getId());
        //获取自增id
        Integer checkGroupId = checkGroup.getId();
        //增加关系
        addCheckGroupAndCheckitem(checkGroupId,checkitemIds);
    }

    //查询所有防疫组
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

    //增加关系
    public void addCheckGroupAndCheckitem(Integer checkGroupId,Integer[] checkitemIds){
        if(checkitemIds!=null&&checkitemIds.length>0){
            for (Integer checkitemId : checkitemIds) {
                HashMap<String,Integer> map = new HashMap<>();
                map.put("checkgroup_id",checkGroupId);
                map.put("checkitem_id",checkitemId);
                checkGroupDao.addCheckGroupAndCheckitem(map);
            }
        }
    }
}
