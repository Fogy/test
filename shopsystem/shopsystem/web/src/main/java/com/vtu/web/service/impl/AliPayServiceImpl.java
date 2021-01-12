package com.vtu.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.vtu.web.service.AliPayService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Map;

@Component
public class AliPayServiceImpl implements AliPayService {
    @Override
    public String aliPayRecharge(JSONObject jsonObject) {
        return null;
    }

    @Override
    public String aliPayShopping(JSONObject jsonObject) {
        return null;
    }

    @Override
    public String aliPayVip(JSONObject jsonObject) {
        return null;
    }

    @Override
    public String aliPayNotify(JSONObject jsonObject) {
        return null;
    }


//    @Override
//    public String aliPayNotify(HttpServletRequest request) {
//        return null;
//    }
}
