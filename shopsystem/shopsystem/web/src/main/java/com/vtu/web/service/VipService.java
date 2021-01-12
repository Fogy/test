package com.vtu.web.service;

import com.alibaba.fastjson.JSONObject;
import com.vtu.model.BaseResult;
import com.vtu.web.service.impl.VipServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service", fallback = VipServiceImpl.class)
public interface VipService {
    @RequestMapping(value = "/get/customerVip",method = RequestMethod.POST)
    BaseResult getCustomerVip(@RequestParam("accountId") Long accountId);

    @RequestMapping(value = "/get/vipDetails",method = RequestMethod.POST)
    BaseResult getVipDetails(@RequestParam("vipType") Long vipType);

    @RequestMapping(value = "/getVipById",method = RequestMethod.POST)
    BaseResult getVipById(@RequestParam("vipId") Long vipId);

    @RequestMapping(value = "/sellVip",method = RequestMethod.POST)
    BaseResult sellVip(@RequestParam("accountId") Long accountId, @RequestParam("vipId") Long vipId);

    @RequestMapping(value = "/recharVipPay",method = RequestMethod.POST)
    BaseResult recharVipPay(@RequestBody JSONObject jsonObject);
}
