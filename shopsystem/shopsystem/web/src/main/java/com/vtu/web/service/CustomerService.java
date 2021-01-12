package com.vtu.web.service;

import com.vtu.model.Customer;
import com.vtu.web.service.impl.CustomerServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "service",fallback = CustomerServiceImpl.class)
@Component
public interface CustomerService {

     @PostMapping("/editCustomer")
     void editCustomer(Customer customer);

     /**
      * 根据用户名获取作品
      * @return
      */
     @RequestMapping(value = "/getCustomerById",method = RequestMethod.POST)
     Customer getCustomerById(@RequestBody Long CustomerId);
}
