package com.yang.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yang.dao.OrderSettingDao;
import com.yang.pojo.OrderSetting;
import com.yang.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;
    @Override
    public void add(List<OrderSetting> data) {
        if(data != null && data.size()>0){
            for (OrderSetting orderSetting : data) {
                Long countByOrderDate= orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if(countByOrderDate>0){
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                }else {
                    orderSettingDao.add(orderSetting);
                }
            }
        }

    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        String begin = date+"-1";
        String end = date+"-31";
        Map map = new HashMap();
        map.put("begin",begin);
        map.put("end",end);
        List<OrderSetting> orderSettingList = orderSettingDao.getOrderSettingByMonth(map);
        List<Map> mapList = new ArrayList<>();
        for (OrderSetting orderSetting : orderSettingList) {
            Map orderSettingMap = new HashMap();
            orderSettingMap.put("date",orderSetting.getOrderDate().getDate());
            orderSettingMap.put("number",orderSetting.getNumber());
            orderSettingMap.put("reservations",orderSetting.getReservations());
            mapList.add(orderSettingMap);
        }
        return mapList;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        Long countByOrderDate= orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if(countByOrderDate>0){
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else {
            orderSettingDao.add(orderSetting);
        }
    }
}
