package com.vtu.mange.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.vtu.mange.service.CustomerService;
import com.vtu.model.BaseResult;
import com.vtu.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/shopMange")
public class ShoperManageController {

    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "/index")
    public String index(){
        return "shoperMange/index";
    }

    /**
     *获取商家列表
     * @return
     */
    @RequestMapping(value = "/getPageCustomer",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map getWorks(Customer customer, Integer pageSize, Integer page){
        JSONObject jsonObject = new JSONObject();
        customer.setIsShoper("true");
        jsonObject.put("customer",customer);
        jsonObject.put("pageNum",page+1);
        jsonObject.put("pageSize",pageSize);
        PageInfo pageInfo = customerService.getPageCustomer(jsonObject);
        Map<String,Object> map = new HashMap<>();
        map.put("data",pageInfo.getList());
        map.put("recordsFiltered",pageInfo.getTotal());
        map.put("recordsTotal",pageInfo.getSize());
        map.put("pages",pageInfo.getPages());
        map.put("pageSize",pageInfo.getPageSize());
        map.put("pageNum",pageInfo.getPageNum());
        return map;
    }

    /**
     * 查询用户
     * @param accountId
     * @return
     */
    @RequestMapping(value = "/getCustomerById")
    @ResponseBody
    public Customer getCustomerById(Long accountId){
        Customer customer = customerService.getCustomerById(accountId);
        return customer;
    }

    /**
     * 提交审核
     * @param customer
     * @return
     */
    @RequestMapping(value = "/submitApply")
    @ResponseBody
    public BaseResult submitApply(Customer customer){
        BaseResult baseResult = customerService.submitApply(customer);
        return baseResult;
    }
}
