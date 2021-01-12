package com.vtu.mapper;

import com.vtu.model.AlipayOrder;

public interface AlipayOrderMapper {
    int deleteByPrimaryKey(String outTradeNo);

    int insert(AlipayOrder record);

    int insertSelective(AlipayOrder record);

    AlipayOrder selectByPrimaryKey(String outTradeNo);

    int updateByPrimaryKeySelective(AlipayOrder record);

    int updateByPrimaryKey(AlipayOrder record);
}