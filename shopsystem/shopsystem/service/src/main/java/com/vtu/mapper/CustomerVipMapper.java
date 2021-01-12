package com.vtu.mapper;

import com.vtu.model.CustomerVip;

public interface CustomerVipMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CustomerVip record);

    int insertSelective(CustomerVip record);

    CustomerVip selectByPrimaryKey(Long id);

    CustomerVip selectByAccountId(Long accountId);

    int updateByPrimaryKeySelective(CustomerVip record);

    int updateByPrimaryKey(CustomerVip record);
}