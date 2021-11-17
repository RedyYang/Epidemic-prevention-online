package com.yang.controller;

import com.yang.constant.MessageConstant;
import com.yang.constant.RedisMessageConstant;
import com.yang.utils.MailUtils;
import com.yang.utils.ValidateCodeUtils;
import com.yang.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result send4Order(String email){
        Integer validateCode = ValidateCodeUtils.generateValidateCode(4);

        try {
            MailUtils.sendMail(email,"您的验证码是："+validateCode.toString()+"，有效时间为5分钟，请不要告诉他人。","防疫在线验证码");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        jedisPool.getResource().setex(email+ RedisMessageConstant.SENDTYPE_ORDER,300,validateCode.toString());
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    //用户邮箱快速登录发送验证码
    @RequestMapping("/send4Login")
    public Result send4Login(String email){
        //随机生成6位数字验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
        //给用户发送验证码
        try{
            MailUtils.sendMail(email,"您的验证码是："+validateCode.toString()+"，有效时间为5分钟，请不要告诉他人。","防疫在线验证码");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        //将验证码保存到redis（5分钟）
        jedisPool.getResource().setex(email + RedisMessageConstant.SENDTYPE_LOGIN,300,validateCode.toString());
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

}
