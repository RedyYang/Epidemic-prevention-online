package com.yang.dao;

import com.github.pagehelper.Page;
import com.yang.pojo.Member;

import java.util.List;

public interface MemberDao {

    void add(Member member);

    Member findByEmail(String email);

    Integer findMemberCountBeforeDate(String date);

    public List<Member> findAll();
    public Page<Member> selectByCondition(String queryString);

    public void deleteById(Integer id);
    public Member findById(Integer id);

    public void edit(Member member);

    public Integer findMemberCountByDate(String date);
    public Integer findMemberCountAfterDate(String date);
    public Integer findMemberTotalCount();

}
