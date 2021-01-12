package com.vtu.service.impl;

import com.vtu.mapper.PersonalBalanceMapper;
import com.vtu.model.PersonalBalance;
import com.vtu.service.PersonalBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PersonalBalanceServiceImpl implements PersonalBalanceService {

    @Autowired
    PersonalBalanceMapper personalBalanceMapper;
    @Override
    public PersonalBalance getPersonalBalanceByAccountId(Long accountId) {
        return personalBalanceMapper.getPersonalBalanceByAccountId(accountId);
    }
}
