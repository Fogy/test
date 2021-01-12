package com.vtu.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.vtu.model.BaseResult;
import com.vtu.web.service.VipService;
import org.springframework.stereotype.Component;

@Component
public class VipServiceImpl implements VipService {
    @Override
    public BaseResult getCustomerVip(Long accountId) {
        return null;
    }

    @Override
    public BaseResult getVipDetails(Long vipType) {
        return null;
    }

    @Override
    public BaseResult getVipById(Long vipId) {
        return null;
    }

    @Override
    public BaseResult sellVip(Long accountId, Long vipId) {
        return null;
    }

    @Override
    public BaseResult recharVipPay(JSONObject jsonObject) {
        return null;
    }
}
