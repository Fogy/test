package com.vtu.service;

import com.alipay.api.AlipayApiException;

import java.math.BigDecimal;
import java.util.Map;

import com.vtu.epay.alipay.AlipayBean;

import javax.servlet.http.HttpServletRequest;

public interface AliPayService {

    String aliPayRecharge(Long accountId, BigDecimal payAmount) throws AlipayApiException;
    String aliPayShopping(Long accountId, Long worksId, String balance) throws AlipayApiException;
    String aliPayNotify(HttpServletRequest request);
    String aliPayVip(Long accountId, Long vipId) throws AlipayApiException;
    String aliPayNotify(Map<String, String> params);
    public void aliPayToUser(Long cashOutId);
}
