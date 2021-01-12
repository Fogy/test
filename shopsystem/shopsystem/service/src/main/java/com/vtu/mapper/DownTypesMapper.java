package com.vtu.mapper;

import com.vtu.model.DownTypes;

public interface DownTypesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DownTypes record);

    int insertSelective(DownTypes record);

    DownTypes selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DownTypes record);

    int updateByPrimaryKey(DownTypes record);
}