package com.vtu.mapper;

import com.vtu.model.WorksDiscount;

public interface WorksDiscountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WorksDiscount record);

    int insertSelective(WorksDiscount record);

    WorksDiscount selectByPrimaryKey(Long id);

    WorksDiscount selectByWorksId(Long worksId);

    int updateByPrimaryKeySelective(WorksDiscount record);

    int updateByPrimaryKey(WorksDiscount record);
}