package com.vtu.mapper;

import com.vtu.model.Extract;

public interface ExtractMapper {
    int deleteByPrimaryKey(Long extractId);

    int insert(Extract record);

    int insertSelective(Extract record);

    Extract selectByPrimaryKey(Long extractId);

    int updateByPrimaryKeySelective(Extract record);

    int updateByPrimaryKey(Extract record);
}