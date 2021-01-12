package com.vtu.web.service;

import com.github.pagehelper.PageInfo;
import com.vtu.model.RechargeRecord;
import com.vtu.web.service.impl.PersonalBalanceServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(value = "service",fallback = PersonalBalanceServiceImpl.class)
public interface PersonalBalanceService {
    @RequestMapping(value = "/getPersonalBalance",method = RequestMethod.POST)
    BigDecimal getPersonalBalanceByAccountId(@RequestParam("accountId") Long accountId);

    @RequestMapping(value = "/getRechargeRecords",method = RequestMethod.GET)
    PageInfo<RechargeRecord> getPersonalBalanceByAccountId(@RequestParam("accountId") Long accountId,
                                                           @RequestParam("pageNum") int pageNum,
                                                           @RequestParam("pageSize") int pageSize);
}
