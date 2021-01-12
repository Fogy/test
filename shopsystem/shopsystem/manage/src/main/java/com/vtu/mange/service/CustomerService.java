package com.vtu.mange.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.vtu.mange.service.impl.CustomerServiceImpl;
import com.vtu.model.BaseResult;
import com.vtu.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "service",fallback = CustomerServiceImpl.class)
public interface CustomerService {

    @RequestMapping(value = "/getPageCustomer",method = RequestMethod.POST)
    PageInfo getPageCustomer(@RequestBody JSONObject jsonObject);

    @RequestMapping(value = "/getCustomerById",method = RequestMethod.POST)
    Customer getCustomerById(@RequestBody Long customerId);

    @RequestMapping(value = "/submitApply",method = RequestMethod.POST)
    BaseResult submitApply(@RequestBody  Customer customer);
}
