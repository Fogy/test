package com.vtu.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vtu.model.BaseResult;
import com.vtu.model.Customer;
import com.vtu.model.Works;
import com.vtu.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @PostMapping("/editCustomer")
    public String editCustomer(@RequestBody Customer customer,HttpServletRequest request){
        customerService.editCustomer(customer);
        return "success";
    }

    @PostMapping("/customer")
    public BaseResult submitApply(@RequestBody Customer customer){
       return customerService.updateByPrimaryKeySelective(customer);
    }

    /**
     * 根据用户ID获取用户
     * @return
     */
    @RequestMapping(value = "/getCustomerById")
    public Customer getCustomerById(@RequestBody Long customerId){
        return customerService.getCustomerById(customerId);
    }

    /**
     * 获取Customer分页
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/getPageCustomer")
    public PageInfo getListCustomer(@RequestBody JSONObject jsonObject){
        Customer customer = jsonObject.getObject("customer",Customer.class);
        PageHelper.startPage(jsonObject.getInteger("pageNum"),
                jsonObject.getInteger("pageSize"));
        List<Customer> listCustomer = customerService.getListCustomer(customer);
        PageInfo pageInfo = new PageInfo(listCustomer);
        return pageInfo;
    }
}
