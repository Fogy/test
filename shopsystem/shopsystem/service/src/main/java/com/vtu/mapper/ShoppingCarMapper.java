package com.vtu.mapper;

import com.vtu.model.ShoppingCar;

public interface ShoppingCarMapper {
    int deleteByPrimaryKey(Long shoppingCarId);

    int insert(ShoppingCar record);

    int insertSelective(ShoppingCar record);

    ShoppingCar selectByPrimaryKey(Long shoppingCarId);

    int updateByPrimaryKeySelective(ShoppingCar record);

    int updateByPrimaryKey(ShoppingCar record);
}