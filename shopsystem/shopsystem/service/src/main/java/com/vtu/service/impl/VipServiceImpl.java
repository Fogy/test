package com.vtu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.vtu.component.AliPayComponent;
import com.vtu.epay.alipay.AlipayBean;
import com.vtu.epay.alipay.AlipayProperties;
import com.vtu.mapper.*;
import com.vtu.model.*;
import com.vtu.service.VipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class VipServiceImpl implements VipService {
    @Autowired
    CustomerVipMapper customerVipMapper;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    VipDetailMapper vipDetailMapper;

    @Autowired
    VipMapper vipMapper;

    @Autowired
    AliPayComponent aliPayComponent;

    @Autowired
    VipOrderMapper vipOrderMapper;

    @Value("${payVipNotifyUrl}")
    private String notifyUrl;

    @Value("${payVipReturnUrl}")
    private String returnUrl;

    @Override
    public BaseResult getCustomerVip(Long accountId) {
        BaseResult baseResult = new BaseResult();
        CustomerVip customerVip = customerVipMapper.selectByAccountId(accountId);
        if (customerVip != null){
            baseResult.setCode(200);
            baseResult.setValue(customerVip);
            return baseResult;
        } else {
            baseResult.setCode(1001);
            baseResult.setMsg("您尚未开通vip！");
        }
        return baseResult;
    }

    @Override
    public BaseResult getVipDetails(Long vipType) {
        BaseResult baseResult = new BaseResult();
        List<Vip> vipList = vipMapper.selectByVipType(vipType);

        baseResult.setValue(vipList);
        return baseResult;
    }

    @Override
    public BaseResult getVipById(Long vipId) {
        BaseResult baseResult = new BaseResult();
        VipDetail vip = vipMapper.selectByPrimaryKey(vipId);
        baseResult.setValue(vip);
        return baseResult;
    }

    /**
     * 购买vip
     * @param accountId
     * @param vipId
     * @return
     */
    @Override
    public BaseResult sellVip(Long vipId,Long accountId) {
        BaseResult baseResult = new BaseResult();
        Customer customer = customerMapper.selectByPrimaryKey(accountId);
        VipDetail vip = vipMapper.selectByPrimaryKey(vipId);
        BigDecimal remain = customer.getRemain();
        BigDecimal prices = vip.getPrices();



        if(remain.compareTo(prices)>=0){
            remain = remain.subtract(prices);
        }else {
            //返回支付宝充值
            try {
                AlipayBean alipayBean = aliPayComponent.getPayBean("sellVip", String.valueOf(remain));
                AlipayTradePagePayRequest alipayTradePagePayRequest = aliPayComponent.getAlipayTradePagePayRequest(returnUrl, notifyUrl, alipayBean);

                VipOrder vipOrder = new VipOrder();
                // 初始化 AlipayOrder 支付宝订单 model
                String appId = AlipayProperties.getAppId();
                vipOrder.setOutTradeNo(alipayBean.getOut_trade_no());
                vipOrder.setTotalAmount(new BigDecimal(alipayBean.getTotal_amount()));
                vipOrder.setSubject(alipayBean.getSubject());
                vipOrder.setAccountId(accountId);
                vipOrder.setStatus("process");
                vipOrder.setAppId(appId);
                vipOrder.setVipDetailId(vipId);
                vipOrderMapper.insert(vipOrder);
                String payRes = aliPayComponent.pageExecute(alipayTradePagePayRequest);
                baseResult.setValue(payRes);
                baseResult.setCode(201);
                return baseResult;
            } catch (AlipayApiException e) {
                baseResult.setCode(BaseResultCode.SERVER_ERROR.getCode());
                baseResult.setMsg("支付异常");
                e.printStackTrace();
            }
        }
        return baseResult;
    }

    @Override
    public BaseResult recharVipPay(JSONObject jsonObject) {
        /**
         * 解析jsonObject
         * 更新树胶状态
         * 插入vip
         */
        String outTrandeNo = jsonObject.getString("out_trade_no");
        String appId = jsonObject.getString("app_id");
        VipOrder vipOrder = new VipOrder();
        vipOrder.setOutTradeNo(outTrandeNo);
        vipOrder.setAppId(appId);
        vipOrder.setStatus("success");
        int update = vipOrderMapper.update(vipOrder);
        vipOrder = vipOrderMapper.selectByPrimaryKey(outTrandeNo);
        long vipDetailId = vipOrder.getVipDetailId();
        VipDetail vipDetail = vipDetailMapper.selectByPrimaryKey(vipDetailId);


        CustomerVip customerVip = new CustomerVip();
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH,vipDetail.getTimeLong());

        customerVip.setAccountId(vipOrder.getAccountId());
        customerVip.setVipDetailId(vipOrder.getVipDetailId());
        customerVip.setEndTime(c.getTime());
        customerVip.setStartTime(date);

        customerVipMapper.insert(customerVip);

        return new BaseResult();
    }
}
