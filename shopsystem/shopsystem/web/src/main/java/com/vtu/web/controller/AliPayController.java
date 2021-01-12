package com.vtu.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vtu.model.BaseResult;
import com.vtu.web.service.AliPayService;
import com.vtu.web.util.AliPayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class AliPayController {
    @Autowired
    AliPayService aliPayService;

    @RequestMapping(value = "/alipay/recharge", method = RequestMethod.POST)
    public String aliPayRecharge(@RequestBody JSONObject jsonObject) {
        return aliPayService.aliPayRecharge(jsonObject);
    }

    @RequestMapping(value = "/alipay/shopping", method = RequestMethod.POST)
    public String aliPayShopping(@RequestBody JSONObject jsonObject) {
        return aliPayService.aliPayShopping(jsonObject);
    }

    @RequestMapping(value = "/alipay/vip", method = RequestMethod.POST)
    public String aliPayVip(@RequestBody JSONObject jsonObject) {
        return aliPayService.aliPayVip(jsonObject);
    }

    @RequestMapping(value = "/alipay/return", method = RequestMethod.GET)
    public String aliPayReturn(HttpServletRequest request){
        System.out.println(request.getParameter("out_trade_no"));
        System.out.println(request.getParameter("total_amount"));
        System.out.println(request.getParameter("sign"));
        System.out.println(request.getParameter("timestamp"));

        Map<String, String> params =  AliPayUtils.convertRequestParamsToMap(request);

        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(params));
        return aliPayService.aliPayNotify(jsonObject);
    }



//    @RequestMapping(value = "/alipay/notify", method = RequestMethod.POST)
//    public String aliPayNotify(HttpServletRequest request){
//        System.out.println("===================================");
//        System.out.println(request.getParameter("out_trade_no"));
//        System.out.println(request.getParameter("total_amount"));
//        System.out.println(request.getParameter("sign"));
//        System.out.println(request.getParameter("timestamp"));
//        return aliPayService.aliPayNotify(request);
//    }
}
