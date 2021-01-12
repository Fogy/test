package com.vtu.web.service.impl;

import com.vtu.model.Customer;
import com.vtu.web.service.CustomerService;
import org.springframework.stereotype.Component;


@Component
public class CustomerServiceImpl implements CustomerService {
    @Override
    public void editCustomer(Customer customer) {

    }

    @Override
    public Customer getCustomerById(Long CustomerId) {
        return null;
    }
}
