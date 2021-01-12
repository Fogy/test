package com.vtu.mapper;

import com.vtu.model.VipDetail;

public interface VipDetailMapper {
    int deleteByPrimaryKey(Long vipDetailId);

    int insert(VipDetail record);

    int insertSelective(VipDetail record);

    VipDetail selectByPrimaryKey(Long vipDetailId);

    int updateByPrimaryKeySelective(VipDetail record);

    int updateByPrimaryKey(VipDetail record);
}