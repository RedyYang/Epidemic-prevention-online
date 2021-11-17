package com.yang.service;

import com.yang.pojo.User;

public interface UserService {
    public User findByUsername(String username);
}
