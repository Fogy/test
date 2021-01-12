package com.vtu.mapper;

import com.vtu.model.Shopper;

import java.util.List;

public interface ShopperMapper {
    int deleteByPrimaryKey(Long shopperId);

    int insert(Shopper record);

    int insertSelective(Shopper record);

    Shopper selectByPrimaryKey(Long shopperId);

    int updateByPrimaryKeySelective(Shopper record);

    int updateByPrimaryKey(Shopper record);

    Shopper selectByAccountId(Long accountId);

    List<Shopper> selectByAuthenicat(String isAuthenicat);

}