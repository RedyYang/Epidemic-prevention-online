package com.yang.dao;

import com.yang.pojo.User;

public interface UserDao {
    public User findByUsername(String username);
}
