package com.yang.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yang.constant.MessageConstant;
import com.yang.constant.RedisMessageConstant;
import com.yang.entity.Result;
import com.yang.pojo.Order;
import com.yang.service.OrderService;
import com.yang.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private OrderService orderService;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map){
        String email = (String)map.get("email");
        String validateCodeInRedis = jedisPool.getResource().get(email + RedisMessageConstant.SENDTYPE_ORDER);
        String validateCode = (String) map.get("validateCode");
        if(validateCode !=null && validateCodeInRedis != null &&validateCode.equals(validateCodeInRedis)){
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            Result result = null;
            try {
                result = orderService.order(map);
            } catch (Exception e) {
                e.printStackTrace();
                return result;
            }
            if(result.isFlag()){
                try {
                    MailUtils.sendMail(email,"预约成功，您预约的时间为："+(String)map.get("orderDate"),"防疫在线预约成功通知");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return result;
        }else {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }

    //根据预约ID查询预约相关信息
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{
            Map map = orderService.findById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
