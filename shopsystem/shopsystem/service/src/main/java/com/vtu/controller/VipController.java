package com.vtu.controller;

import com.alibaba.fastjson.JSONObject;
import com.vtu.model.BaseResult;
import com.vtu.service.impl.VipServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VipController {

    @Autowired
    VipServiceImpl vipService;

    @RequestMapping(value = "/get/vipDetails")
    public BaseResult getVipDetails(@RequestParam("vipType") Long vipType) {
        BaseResult baseResult = vipService.getVipDetails(vipType);
        return baseResult;
    }

    @RequestMapping(value = "/getVipById")
    public BaseResult getVipById(@RequestParam("vipId") Long vipId){
        BaseResult baseResult = vipService.getVipById(vipId);
        return baseResult;
    }

    @RequestMapping(value = "/sellVip")
    public BaseResult getVipById(@RequestParam("accountId") Long accountId, @RequestParam("vipId") Long vipId){
        BaseResult baseResult = vipService.sellVip(accountId,vipId);
        return baseResult;
    }
    @RequestMapping(value = "/recharVipPay")
    public BaseResult recharVipPay(@RequestBody JSONObject jsonObject){
        BaseResult baseResult = vipService.recharVipPay(jsonObject);
        return baseResult;
    }

}
