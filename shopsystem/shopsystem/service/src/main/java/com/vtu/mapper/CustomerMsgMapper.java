package com.vtu.mapper;

import com.vtu.model.CustomerMsg;

public interface CustomerMsgMapper {
    int deleteByPrimaryKey(Long customerMsgId);

    int insert(CustomerMsg record);

    int insertSelective(CustomerMsg record);

    CustomerMsg selectByPrimaryKey(Long customerMsgId);

    int updateByPrimaryKeySelective(CustomerMsg record);

    int updateByPrimaryKey(CustomerMsg record);
}