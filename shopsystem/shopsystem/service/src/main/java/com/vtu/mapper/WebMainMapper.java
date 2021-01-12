package com.vtu.mapper;

import com.vtu.model.WebMain;

public interface WebMainMapper {
    int insert(WebMain record);

    int insertSelective(WebMain record);
}