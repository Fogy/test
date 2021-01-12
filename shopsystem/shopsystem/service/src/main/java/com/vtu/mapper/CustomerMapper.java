package com.vtu.mapper;

import com.vtu.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerMapper {
    int deleteByPrimaryKey(Long accountId);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Long accountId);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

    Customer selectByEmail(String email);

    Customer selectByAuthCode(String authCode);

    Customer selectByAuthCodeAndEmail(String authCode, String email);

    List<Customer> selectByCustomer(Customer customer);
}