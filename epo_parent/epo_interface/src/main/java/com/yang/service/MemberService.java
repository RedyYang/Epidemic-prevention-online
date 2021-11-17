package com.yang.service;

import com.yang.pojo.Member;

import java.util.List;

public interface MemberService {
    //根据手机号查询会员
    public Member findByEmail(String email);
    public void add(Member member);
    public List<Integer> findMemberCountByMonths(List<String> months);
}
