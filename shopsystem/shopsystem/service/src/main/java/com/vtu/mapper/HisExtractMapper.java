package com.vtu.mapper;

import com.vtu.model.HisExtract;

public interface HisExtractMapper {
    int deleteByPrimaryKey(Long hisExtractId);

    int insert(HisExtract record);

    int insertSelective(HisExtract record);

    HisExtract selectByPrimaryKey(Long hisExtractId);

    int updateByPrimaryKeySelective(HisExtract record);

    int updateByPrimaryKeyWithBLOBs(HisExtract record);

    int updateByPrimaryKey(HisExtract record);
}