package com.yang.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yang.dao.MemberDao;
import com.yang.pojo.Member;
import com.yang.service.MemberService;
import com.yang.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员服务
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;

    public Member findByEmail(String email) {
        return memberDao.findByEmail(email);
    }

    //保存会员信息
    public void add(Member member) {
        String password = member.getPassword();
        if(password != null){
            //使用md5将明文密码进行加密
            password = MD5Utils.md5(password);
            member.setPassword(password);
        }
        memberDao.add(member);
    }

    //根据月份查询会员数量
    public List<Integer> findMemberCountByMonths(List<String> months) {//2018.05
        List<Integer> memberCount = new ArrayList<>();
        for (String month : months) {
            String date = month + ".31";//2018.05.31
            Integer count = memberDao.findMemberCountBeforeDate(date);
            memberCount.add(count);
        }
        return memberCount;
    }
}
