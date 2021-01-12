package com.vtu.service.impl;

import com.vtu.mapper.TestMapper;
import com.vtu.model.Test;
import com.vtu.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    TestMapper testMapper;
    @Override
    public Test getTest(Integer id) {
        return testMapper.selectByPrimaryKey(id);
    }
}
