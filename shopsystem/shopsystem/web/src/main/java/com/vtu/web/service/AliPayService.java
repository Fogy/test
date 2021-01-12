package com.vtu.web.service;

import com.alibaba.fastjson.JSONObject;
import com.vtu.web.service.impl.AliPayServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@FeignClient(value = "service", fallback = AliPayServiceImpl.class)
public interface AliPayService {
    @RequestMapping(value = "/alipay/recharge", method = RequestMethod.POST)
    String aliPayRecharge(@RequestBody JSONObject jsonObject);
    @RequestMapping(value = "/alipay/shopping", method = RequestMethod.POST)
    String aliPayShopping(@RequestBody JSONObject jsonObject);
    @RequestMapping(value = "/alipay/vip", method = RequestMethod.POST)
    String aliPayVip(@RequestBody JSONObject jsonObject);
    @RequestMapping(value = "/alipay/notify/web", method = RequestMethod.POST)
    String aliPayNotify(@RequestBody JSONObject jsonObject);
}
