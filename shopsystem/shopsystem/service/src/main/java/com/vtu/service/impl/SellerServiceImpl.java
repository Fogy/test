package com.vtu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.vtu.mapper.CashOutMapper;
import com.vtu.mapper.ShopperMapper;
import com.vtu.model.BaseResult;
import com.vtu.model.CashOut;
import com.vtu.model.Shopper;
import com.vtu.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    CashOutMapper cashOutMapper;
    @Autowired
    ShopperMapper shopperMapper;

    @Override
    public BaseResult cashOutApply(JSONObject jsonObject) {
        Long accountId = jsonObject.getLong("accountId");
        BigDecimal amount = jsonObject.getBigDecimal("amount");
        CashOut cashOut = cashOutMapper.selectByAccountIdAndStatus(accountId,"Auditing");
        if(cashOut != null){
            BaseResult baseResult = new BaseResult();
            baseResult.setMsg("已有正在审核的提现记录，不能再次发起提现。");
            baseResult.setCode(500);
            return baseResult;
        } else {
            BaseResult baseResult = new BaseResult();
            Shopper shopper = shopperMapper.selectByAccountId(accountId);
            BigDecimal currentCash = shopper.getCash();
            if (currentCash.compareTo(amount) < 0){
                baseResult.setMsg("提现金额大于商户余额，无法进行提现申请！");
                baseResult.setCode(500);
                return baseResult;
            }
            CashOut cashOut1 = new CashOut();
            cashOut1.setAccountId(accountId);
            cashOut1.setAmount(amount);
            cashOut1.setApplyTime(Calendar.getInstance().getTime());
            cashOut1.setStatus("Auditing");
            cashOutMapper.insert(cashOut1);

            baseResult.setMsg("提现申请成功！");
            baseResult.setCode(200);
            return baseResult;
        }
    }
}
