package com.vtu.mapper;

import com.vtu.model.PersonalBalance;

import java.math.BigDecimal;

public interface PersonalBalanceMapper {
    int deleteByPrimaryKey(Long personalMoneyId);

    int insert(PersonalBalance record);

    int insertSelective(PersonalBalance record);

    PersonalBalance selectByPrimaryKey(Long personalMoneyId);

    int updateByPrimaryKeySelective(PersonalBalance record);

    int updateByPrimaryKey(PersonalBalance record);

    PersonalBalance getPersonalBalanceByAccountId(Long accountId);
}