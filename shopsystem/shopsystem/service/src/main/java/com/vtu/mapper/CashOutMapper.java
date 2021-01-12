package com.vtu.mapper;

import com.vtu.model.CashOut;

public interface CashOutMapper {
    int deleteByPrimaryKey(Long cashOutId);

    int insert(CashOut record);

    int insertSelective(CashOut record);

    CashOut selectByPrimaryKey(Long cashOutId);

    CashOut selectByAccountIdAndStatus(Long accountId, String status);

    int updateByPrimaryKeySelective(CashOut record);

    int updateByPrimaryKey(CashOut record);
}