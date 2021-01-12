package com.vtu.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.vtu.model.BaseResult;
import com.vtu.service.impl.AliPayServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Map;

@RestController
public class AliPayController {
    private static Logger logger = LoggerFactory.getLogger(AliPayController.class);
    @Autowired
    AliPayServiceImpl aliPayService;

    @RequestMapping(value = "/alipay/recharge", method = RequestMethod.POST)
    public String aliPayRecharge(@RequestBody JSONObject jsonObject) throws AlipayApiException {
        Long accountId = jsonObject.getLong("accountId");
        BigDecimal payAmount = jsonObject.getBigDecimal("payAmount");
        return aliPayService.aliPayRecharge(accountId, payAmount);
    }

    @RequestMapping(value = "/alipay/shopping", method = RequestMethod.POST)
    public String aliPayShopping(@RequestBody JSONObject jsonObject) throws AlipayApiException {
        Long accountId = jsonObject.getLong("accountId");
        Long worksId = Long.parseLong(jsonObject.getString("worksIds"));
        String balance = jsonObject.getString("balance");
        return aliPayService.aliPayShopping(accountId, worksId, balance);
    }

    @RequestMapping(value = "/alipay/vip", method = RequestMethod.POST)
    public String aliPayVip(@RequestBody JSONObject jsonObject) throws AlipayApiException {
        Long accountId = jsonObject.getLong("accountId");
        Long vipDetailId = jsonObject.getLong("vipDetailId");
        return aliPayService.aliPayVip(accountId, vipDetailId);
    }

    @RequestMapping(value = "/alipay/return", method = RequestMethod.GET)
    public String aliPayReturn(HttpServletRequest request, HttpServletResponse response){
        System.out.println(request.getParameter("out_trade_no"));
        System.out.println(request.getParameter("total_amount"));
        System.out.println(request.getParameter("sign"));
        System.out.println(request.getParameter("timestamp"));

        return aliPayService.aliPayNotify(request);
    }

    @RequestMapping(value = "/service/alipay/notify", method = RequestMethod.POST)
    public String aliPayNotifyService(HttpServletRequest request){
        System.out.println("===================================");
        System.out.println(request.getParameter("out_trade_no"));
        System.out.println(request.getParameter("total_amount"));
        System.out.println(request.getParameter("sign"));
        System.out.println(request.getParameter("timestamp"));
        return aliPayService.aliPayNotify(request);
    }

    @RequestMapping(value = "/alipay/notify/web", method = RequestMethod.POST)
    public String aliPayNotify(@RequestBody JSONObject jsonObject){
        System.out.println("===================================");
        System.out.println(jsonObject.getString("out_trade_no"));
        System.out.println(jsonObject.getString("total_amount"));
        System.out.println(jsonObject.getString("sign"));
        System.out.println(jsonObject.getString("timestamp"));
        Map params = JSONObject.parseObject(jsonObject.toJSONString());
        return aliPayService.aliPayNotify(params);
    }


    @RequestMapping(value = "/alipay/cashOut", method = RequestMethod.POST)
    public BaseResult cashOut(@RequestBody JSONObject jsonObject){
        aliPayService.aliPayToUser(jsonObject.getLong("cashOutId"));
        BaseResult baseResult = new BaseResult();
        baseResult.setCode(200);
        baseResult.setMsg("处理完成！");
        return baseResult;
    }
}
