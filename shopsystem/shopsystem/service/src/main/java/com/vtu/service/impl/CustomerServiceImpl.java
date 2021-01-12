package com.vtu.service.impl;

import com.vtu.mapper.CustomerMapper;
import com.vtu.model.BaseResult;
import com.vtu.model.BaseResultCode;
import com.vtu.model.Customer;
import com.vtu.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerMapper customerMapper;
    @Override
    public void editCustomer(Customer customer) {
        customerMapper.updateByPrimaryKey(customer);
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        return customerMapper.selectByPrimaryKey(customerId);
    }

    @Override
    public List<Customer> getListCustomer(Customer customer) {
        return customerMapper.selectByCustomer(customer);
    }

    @Override
    public BaseResult updateByPrimaryKeySelective(Customer customer) {
        int count = customerMapper.updateByPrimaryKeySelective(customer);
        BaseResult baseResult = new BaseResult();
        if(count<=0){
            baseResult.setCode(BaseResultCode.SERVER_ERROR.getCode());
            baseResult.setMsg(BaseResultCode.SERVER_ERROR.getMsg());
        }
        return baseResult;
    }
}
