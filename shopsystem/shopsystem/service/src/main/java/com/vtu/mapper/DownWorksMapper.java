package com.vtu.mapper;

import com.vtu.model.DownWorks;

public interface DownWorksMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DownWorks record);

    int insertSelective(DownWorks record);

    DownWorks selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DownWorks record);

    int updateByPrimaryKey(DownWorks record);
}