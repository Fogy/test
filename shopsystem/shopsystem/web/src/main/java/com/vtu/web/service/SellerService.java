package com.vtu.web.service;

import com.alibaba.fastjson.JSONObject;
import com.vtu.model.BaseResult;
import com.vtu.web.service.impl.AliPayServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "service", fallback = AliPayServiceImpl.class)
public interface SellerService {
    @RequestMapping(value = "/cashout/apply", method = RequestMethod.GET)
    BaseResult cashOutApply(@RequestBody JSONObject jsonObject);
}
