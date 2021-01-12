package com.vtu.web.service;

import com.alibaba.fastjson.JSONObject;
import com.vtu.web.service.impl.BalanceServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "service",fallback = BalanceServiceImpl.class)
public interface BalanceService {

    /**
     * 用户充值接口
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/alipay/recharge",method = RequestMethod.POST)
    String recharge(@RequestBody JSONObject jsonObject);
}
