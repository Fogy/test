package com.vtu.mange.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.vtu.mange.service.CustomerService;
import com.vtu.model.BaseResult;
import com.vtu.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerServiceImpl implements CustomerService {
    @Override
    public PageInfo getPageCustomer(JSONObject jsonObject) {
        return null;
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        return null;
    }

    @Override
    public BaseResult submitApply(Customer customer) {
        return null;
    }


}
