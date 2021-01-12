package com.vtu.service;

import com.vtu.model.PersonalBalance;

import java.math.BigDecimal;

public interface PersonalBalanceService {
    PersonalBalance getPersonalBalanceByAccountId(Long accountId);
}
