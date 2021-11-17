package com.yang.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yang.dao.CheckItemDao;
import com.yang.entity.PageResult;
import com.yang.entity.QueryPageBean;
import com.yang.pojo.CheckItem;
import com.yang.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;


    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage  = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckItem> rows = page.getResult();

        return new PageResult(total,rows);
    }

    public void deleteById(Integer id) {
        Long count = checkItemDao.findCountByCheckItemId(id);
        if(count>0){
            throw new RuntimeException("当前防疫项已经被关联到防疫组，不允许删除");
        }else {
            checkItemDao.deleteById(id);
        }

    }

    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }


    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }


    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

}
