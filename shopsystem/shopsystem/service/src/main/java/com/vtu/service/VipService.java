package com.vtu.service;

import com.alibaba.fastjson.JSONObject;
import com.vtu.model.BaseResult;

public interface VipService {
    BaseResult getCustomerVip(Long accountId);
    BaseResult getVipDetails(Long vipType);

    BaseResult getVipById(Long vipType);

    BaseResult sellVip(Long accountId, Long vipId);

    BaseResult recharVipPay(JSONObject jsonObject);
}
