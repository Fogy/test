package com.vtu.mapper;

import com.vtu.model.RechargeRecord;

import java.util.List;

public interface RechargeRecordMapper {
    int deleteByPrimaryKey(Long rechargeId);

    int insert(RechargeRecord record);

    int insertSelective(RechargeRecord record);

    RechargeRecord selectByPrimaryKey(Long rechargeId);

    int updateByPrimaryKeySelective(RechargeRecord record);

    int updateByPrimaryKey(RechargeRecord record);

    List<RechargeRecord> findAllByAccountId(Long accountId);
}