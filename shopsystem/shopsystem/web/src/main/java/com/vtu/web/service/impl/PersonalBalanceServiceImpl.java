package com.vtu.web.service.impl;

import com.github.pagehelper.PageInfo;
import com.vtu.model.RechargeRecord;
import com.vtu.web.service.PersonalBalanceService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PersonalBalanceServiceImpl implements PersonalBalanceService {
    @Override
    public BigDecimal getPersonalBalanceByAccountId(Long accountId) {
        return null;
    }

    @Override
    public PageInfo<RechargeRecord> getPersonalBalanceByAccountId(Long accountId, int start, int length) {
        return null;
    }
}
