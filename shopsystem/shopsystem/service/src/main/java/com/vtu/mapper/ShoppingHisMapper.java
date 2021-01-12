package com.vtu.mapper;

import com.vtu.model.ShoppingHis;

public interface ShoppingHisMapper {
    int deleteByPrimaryKey(Long shoppingHisId);

    int insert(ShoppingHis record);

    int insertSelective(ShoppingHis record);

    ShoppingHis selectByPrimaryKey(Long shoppingHisId);

    int updateByPrimaryKeySelective(ShoppingHis record);

    int updateByPrimaryKey(ShoppingHis record);
}