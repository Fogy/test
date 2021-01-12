package com.vtu.mange.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.vtu.mange.service.CustomerService;
import com.vtu.model.Customer;
import com.vtu.model.Works;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CustomerController {
    CustomerService customerService;

    @RequestMapping(value = "/getPageCustomer",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map getPageCustomer(Customer customer, int pageSize, int pageNum){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("customer",customer);
        jsonObject.put("pageNum",pageNum);
        jsonObject.put("pageSize",pageSize);
        PageInfo pageInfo = customerService.getPageCustomer(jsonObject);

        Map<String,Object> map = new HashMap<>();
        map.put("data",pageInfo.getList());
        map.put("recordsFiltered",pageInfo.getTotal());
        map.put("recordsTotal",pageInfo.getSize());
        return map;

    }
}
