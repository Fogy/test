package com.vtu.model;

import java.math.BigDecimal;

public class PersonalBalance {
    private Long personalMoneyId;

    private Long accountId;

    private BigDecimal balance;

    public Long getPersonalMoneyId() {
        return personalMoneyId;
    }

    public void setPersonalMoneyId(Long personalMoneyId) {
        this.personalMoneyId = personalMoneyId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}