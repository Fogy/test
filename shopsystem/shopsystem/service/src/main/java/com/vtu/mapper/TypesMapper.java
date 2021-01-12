package com.vtu.mapper;

import com.vtu.model.Types;

public interface TypesMapper {
    int deleteByPrimaryKey(Integer typesId);

    int insert(Types record);

    int insertSelective(Types record);

    Types selectByPrimaryKey(Integer typesId);

    int updateByPrimaryKeySelective(Types record);

    int updateByPrimaryKey(Types record);
}