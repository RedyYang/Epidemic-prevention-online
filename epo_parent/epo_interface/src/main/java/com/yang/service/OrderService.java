package com.yang.service;

import com.yang.entity.Result;

import java.util.Map;

public interface OrderService {
    Result order(Map map) throws Exception;
    public Map findById(Integer id) throws Exception;
}
