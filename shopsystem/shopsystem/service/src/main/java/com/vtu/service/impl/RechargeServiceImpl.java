package com.vtu.service.impl;

import com.vtu.mapper.RechargeRecordMapper;
import com.vtu.model.RechargeRecord;
import com.vtu.service.RechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RechargeServiceImpl implements RechargeService {
    @Autowired
    RechargeRecordMapper rechargeRecordMapper;
    @Override
    public List<RechargeRecord> findAllByAccountId(Long accountId) {
        return rechargeRecordMapper.findAllByAccountId(accountId);
    }
}
