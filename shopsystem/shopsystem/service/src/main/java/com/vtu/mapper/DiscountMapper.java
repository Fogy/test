package com.vtu.mapper;

import com.vtu.model.Discount;

public interface DiscountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Discount record);

    int insertSelective(Discount record);

    Discount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Discount record);

    int updateByPrimaryKey(Discount record);
}