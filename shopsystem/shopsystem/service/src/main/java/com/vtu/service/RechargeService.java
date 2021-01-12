package com.vtu.service;

import com.vtu.model.RechargeRecord;

import java.util.List;

public interface RechargeService {
    List<RechargeRecord> findAllByAccountId(Long accountId);
}
