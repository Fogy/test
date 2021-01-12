package com.vtu.mapper;

import com.vtu.model.VipOrder;

public interface VipOrderMapper {
    int deleteByPrimaryKey(String outTradeNo);

    int insert(VipOrder record);

    int insertSelective(VipOrder record);

    VipOrder selectByPrimaryKey(String outTradeNo);

    int updateByPrimaryKeySelective(VipOrder record);

    int updateByPrimaryKey(VipOrder record);

    int update(VipOrder vipOrder);
}