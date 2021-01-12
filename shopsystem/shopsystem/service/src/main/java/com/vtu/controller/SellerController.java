package com.vtu.controller;

import com.alibaba.fastjson.JSONObject;
import com.vtu.model.BaseResult;
import com.vtu.service.impl.SellerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class SellerController {

    @Autowired
    SellerServiceImpl sellerService;

    @RequestMapping(value = "/cashout/apply", method = RequestMethod.GET)
    public BaseResult cashOutApply(@RequestBody JSONObject jsonObject){
        return  sellerService.cashOutApply(jsonObject);
    }
}
