package com.vtu.controller;

import com.vtu.model.PersonalBalance;
import com.vtu.service.impl.PersonalBalanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class PersonalBalanceController {
    @Autowired
    PersonalBalanceServiceImpl personalBalanceService;

    @RequestMapping(value = "/getPersonalBalance")
    public BigDecimal getPersonalBalanceByAccountId(@RequestParam("accountId") Long accountId){
        PersonalBalance pb = personalBalanceService.getPersonalBalanceByAccountId(accountId);
        return pb.getBalance();
    }
}
