package com.vtu.service;

import com.vtu.model.BaseResult;
import com.vtu.model.Customer;

import java.util.List;

public interface CustomerService {
     void editCustomer(Customer customer);

     /**
      * 根据用户ID获取用户
      * @param CustomerId
      * @return
      */
     Customer getCustomerById(Long CustomerId);

     /**
      * 根据条件获取customer
      * @param customer
      * @return
      */
     List<Customer> getListCustomer(Customer customer);

     /**
      * 根据条件更新customer
      * @param customer
      * @return
      */
     BaseResult updateByPrimaryKeySelective(Customer customer);

}
