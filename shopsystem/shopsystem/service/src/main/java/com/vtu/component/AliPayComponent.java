package com.vtu.component;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.vtu.epay.alipay.AlipayBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class AliPayComponent {

    @Autowired
    AlipayClient alipayClient;

    public String pageExecute(AlipayTradePagePayRequest alipayRequest) throws AlipayApiException {
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        return result;
    }

    public AlipayTradePagePayRequest getAlipayTradePagePayRequest(String returnUrl,String notifyUrl,AlipayBean alipayBean){
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(returnUrl);
        alipayRequest.setNotifyUrl(notifyUrl);
        alipayRequest.setBizContent(JSON.toJSONString(alipayBean));
        return alipayRequest;
    }

    public AlipayBean getPayBean(String subject,String total_amount){
        AlipayBean alipayBean = new AlipayBean();
        String out_trade_no = UUID.randomUUID().toString().replaceAll("-","");
        alipayBean.setOut_trade_no(out_trade_no);
        alipayBean.setSubject(subject);
        alipayBean.setTotal_amount(total_amount);
        alipayBean.setTimeout_express("15m");
        return alipayBean;
    }


}
