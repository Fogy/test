package com.vtu.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayAccountExrateSentimentDataSendModel;
import com.alipay.api.domain.AlipayFundTransToaccountTransferModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundTransOrderQueryRequest;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.vtu.component.AliPayComponent;
import com.vtu.controller.AliPayController;
import com.vtu.epay.alipay.AlipayBean;
import com.vtu.epay.alipay.AlipayProperties;
import com.vtu.mapper.*;
import com.vtu.model.*;
import com.vtu.service.AliPayService;
import com.vtu.utils.AliPayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AliPayServiceImpl implements AliPayService {
    private static Logger logger = LoggerFactory.getLogger(AliPayController.class);
    private ExecutorService executorService = Executors.newFixedThreadPool(20);
    @Autowired
    AlipayOrderMapper alipayOrderMapper;
    @Autowired
    WorksDiscountMapper worksDiscountMapper;
    @Autowired
    DiscountMapper discountMapper;
    @Autowired
    WorksMapper worksMapper;

    @Autowired
    PersonalBalanceMapper personalBalanceMapper;
    @Autowired
    VipDetailMapper vipDetailMapper;

    @Autowired
    CustomerVipMapper customerVipMapper;

    @Autowired
    WorksDownloadMapper worksDownloadMapper;

    @Autowired
    CashOutMapper cashOutMapper;

    @Autowired
    ShopperMapper shopperMapper;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    AliPayComponent aliPayComponent;

    /**
     * 支付宝充值订单函数
     * @param accountId
     * @param alipayBean
     * @param alipayOrder
     * @return
     * @throws AlipayApiException
     */
    private String aliPay(Long accountId, AlipayBean alipayBean, AlipayOrder alipayOrder) throws AlipayApiException {

        // 从配置文件中获取支付宝配置参数
        String appId = AlipayProperties.getAppId();
        String returnUrl = AlipayProperties.getReturnUrl();
        String notifyUrl = AlipayProperties.getNotifyUrl();

        // 初始化 AlipayOrder 支付宝订单 model
        alipayOrder.setOutTradeNo(alipayBean.getOut_trade_no());
        alipayOrder.setTotalAmount(new BigDecimal(alipayBean.getTotal_amount()));
        alipayOrder.setSubject(alipayBean.getSubject());
        alipayOrder.setAccountId(accountId);
        alipayOrder.setStatus("process");
        alipayOrder.setAppId(appId);
        alipayOrderMapper.insert(alipayOrder);

        // 发送订单给支付宝，并返回充值页面，用于用户扫码充值
        AlipayTradePagePayRequest alipayRequest = aliPayComponent.getAlipayTradePagePayRequest(returnUrl,notifyUrl,alipayBean);
        String result = aliPayComponent.pageExecute(alipayRequest);

        return result;
    }

    /**
     * 支付宝提现函数
     * @param cashOutId
     */
    public void aliPayToUser(Long cashOutId){
        // 从配置文件中获取支付宝配置参数
        String serverUrl = AlipayProperties.getGatewayUrl();
        String appId = AlipayProperties.getAppId();
        String privateKey = AlipayProperties.getPrivateKey();
        String format = AlipayProperties.getFormat();
        String charset = AlipayProperties.getCharset();
        String alipayPublicKey = AlipayProperties.getPublicKey();
        String signType = AlipayProperties.getSignType();

        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, format, charset, alipayPublicKey, signType);
        AlipayFundTransToaccountTransferRequest transferAccounts_request = new AlipayFundTransToaccountTransferRequest();
        CashOut cashOut = cashOutMapper.selectByPrimaryKey(cashOutId);
        //能够找到cashout记录并且状态是审核中，管理员能看到所有的记录，用户只能看到自己的记录，同时只有一条记录是在审核中
        if (cashOut != null && cashOut.getStatus().equals("Auditing")){
            AlipayFundTransToaccountTransferModel model = new AlipayFundTransToaccountTransferModel();
            model.setOutBizNo("cash_out" + System.currentTimeMillis() + cashOutId.toString());
            model.setPayeeType("ALIPAY_USERID");
            model.setPayeeAccount(cashOut.getPayeeAccount());
            model.setAmount(cashOut.getAmount().toString());
            model.setPayerShowName( AlipayProperties.getPayerShowName() );
            model.setPayeeRealName(cashOut.getPayeeRealName());
            model.setRemark(cashOut.getPayeeRealName() + " " + cashOut.getPayeeAccount() + " 提现 " + cashOut.getAmount().toString());
            transferAccounts_request.setBizModel(model);
            try {
                AlipayFundTransToaccountTransferResponse response = alipayClient.execute(transferAccounts_request);
                if(response.isSuccess()){
                    cashOut.setStatus("success");
                    cashOut.setApplyTime(Calendar.getInstance().getTime());
                    cashOutMapper.updateByPrimaryKey(cashOut);
                    // 更新卖家余额
                    Shopper shopper = shopperMapper.selectByAccountId(cashOut.getAccountId());
                    BigDecimal currentCash = shopper.getCash();
                    BigDecimal newCash = currentCash.subtract(cashOut.getAmount());
                    shopper.setCash(newCash);
                    shopperMapper.updateByPrimaryKey(shopper);
                } else {
                    // 查询转账，如果转账记录成功，则表示已经给客户转账
                    AlipayFundTransOrderQueryRequest queryRequest = new AlipayFundTransOrderQueryRequest();
                    queryRequest.setBizModel(model);
                    AlipayFundTransOrderQueryResponse queryResponse = alipayClient.execute(queryRequest);
                    if (queryResponse.isSuccess()) {
                        cashOut.setStatus("success");
                        cashOut.setApplyTime(Calendar.getInstance().getTime());
                        cashOutMapper.updateByPrimaryKey(cashOut);
                        // 更新卖家余额
                        Shopper shopper = shopperMapper.selectByAccountId(cashOut.getAccountId());
                        BigDecimal currentCash = shopper.getCash();
                        BigDecimal newCash = currentCash.subtract(cashOut.getAmount());
                        shopper.setCash(newCash);
                        shopperMapper.updateByPrimaryKey(shopper);
                    } else {
                        cashOut.setStatus("failure");
                        cashOut.setApplyTime(Calendar.getInstance().getTime());
                        cashOutMapper.updateByPrimaryKey(cashOut);
                    }
                }
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 点击支付宝充值，调用的方法方法
     * @param accountId
     * @param payAmount
     * @return
     * @throws AlipayApiException
     */
    @Override
    public String aliPayRecharge(Long accountId, BigDecimal payAmount) throws AlipayApiException {

        AlipayBean alipayBean = aliPayComponent.getPayBean("recharge",payAmount.toString());
        AlipayOrder alipayOrder = new AlipayOrder();
        return this.aliPay(accountId,alipayBean,alipayOrder);

    }

    @Override
    public String aliPayShopping(Long accountId, Long worksId, String balance) throws AlipayApiException {
        // 获取作品价格
        Works works = worksMapper.selectByPrimaryKey(worksId);

        BigDecimal price = works.getPrice();
        WorksDiscount worksDiscount = worksDiscountMapper.selectByWorksId(worksId);
        //获取作品折扣
        String total_amount;
        if(null != worksDiscount && null != worksDiscount.getDiscountId()){
            Discount discount = discountMapper.selectByPrimaryKey(worksDiscount.getDiscountId());
            Double rate = discount.getDiscountRate();
            // 计算实际价钱
            total_amount = price.subtract(price.multiply(new BigDecimal(rate.toString()))).setScale(2).toString();
        }else {
            total_amount= String.valueOf(price);
        }

        if (balance.equals("true")){
            Customer customer = customerMapper.selectByPrimaryKey(accountId);
            BigDecimal curBalance = customer.getRemain();

            if (-1 != curBalance.compareTo(new BigDecimal(total_amount))) {
                customer.setRemain(curBalance.subtract(new BigDecimal(total_amount)));
                customerMapper.updateByPrimaryKey(customer);

                WorksDownload worksDownload = new WorksDownload();
                worksDownload.setAccountId(accountId);
                worksDownload.setWorksId(worksId);
                worksDownload.setDownTime(new Date());
                worksDownloadMapper.insert(worksDownload);
                return "success";
            }else{
                BigDecimal sub = new BigDecimal(total_amount).subtract(curBalance);
                total_amount = sub.toString();
            }
        }

        // 初始化AliPay参数
        String out_trade_no = "tradeprecreate" + System.currentTimeMillis() + accountId.toString();
        AlipayBean alipayBean = new AlipayBean();
        alipayBean.setOut_trade_no(out_trade_no);
        alipayBean.setSubject("shopping");
        alipayBean.setTotal_amount(total_amount);
        alipayBean.setTimeout_express("15m");

        AlipayOrder alipayOrder = new AlipayOrder();
        alipayOrder.setWorksId(worksId);
        return this.aliPay(accountId, alipayBean, alipayOrder);
    }

    @Override
    public String aliPayNotify(HttpServletRequest request) {
        return null;
    }

    @Override
    public String aliPayVip(Long accountId, Long vipDetailId) throws AlipayApiException {
        //获取vip详细信息
        VipDetail vipDetail = vipDetailMapper.selectByPrimaryKey(vipDetailId);
        // 计算实际价钱
        String total_amount = vipDetail.getPrices().toString();
        // 初始化AliPay参数
        String out_trade_no = "tradeprecreate" + System.currentTimeMillis() + accountId.toString();
        AlipayBean alipayBean = new AlipayBean();
        alipayBean.setOut_trade_no(out_trade_no);
        alipayBean.setSubject("vip");
        alipayBean.setTotal_amount(total_amount);
        alipayBean.setTimeout_express("15m");

        AlipayOrder alipayOrder = new AlipayOrder();
        alipayOrder.setVipDetailId(vipDetailId);
        return this.aliPay(accountId, alipayBean, alipayOrder);
    }

    private void check(Map<String, String> params) throws AlipayApiException {
        String outTradeNo = params.get("out_trade_no");

        // 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
        AlipayOrder order = alipayOrderMapper.selectByPrimaryKey(outTradeNo); // 这个方法自己实现
        if (order == null) {
            throw new AlipayApiException("out_trade_no错误");
        }

        // 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）
        System.out.println(params.get("total_amount"));
        BigDecimal total_amount = new BigDecimal(params.get("total_amount"));
        if (!total_amount.equals(order.getTotalAmount())) {
            throw new AlipayApiException("error total_amount");
        }

        // 3、验证app_id是否为该商户本身。
        if (!params.get("app_id").equals(order.getAppId())) {
            throw new AlipayApiException("app_id不一致");
        }
    }

    private void rechargeNotify(AlipayOrder alipayOrder){
        Long accountId = alipayOrder.getAccountId();
        PersonalBalance personalBalance = personalBalanceMapper.getPersonalBalanceByAccountId(accountId);
        personalBalance.setBalance(personalBalance.getBalance().add(alipayOrder.getTotalAmount()));
        personalBalanceMapper.updateByPrimaryKey(personalBalance);
    }

    private void shoppingNotify(AlipayOrder alipayOrder){
        // 用户付款成功，将用户和作品信息添加到可以下载的列表中
        WorksDownload worksDownload = new WorksDownload();
        worksDownload.setAccountId(alipayOrder.getAccountId());
        worksDownload.setWorksId(alipayOrder.getWorksId());
        worksDownloadMapper.insert(worksDownload);

        // 用户付款成功后，商户的钱会增加，根据订单中的作品的ID找到商户的 acountId，在商户表中增加商户的金额
        Works works = worksMapper.selectByPrimaryKey(alipayOrder.getWorksId());
        Shopper shopper = shopperMapper.selectByAccountId(works.getAccountId());
        BigDecimal cash = shopper.getCash();
        BigDecimal newCash = cash.add(alipayOrder.getTotalAmount());
        shopper.setCash(newCash);
        shopperMapper.updateByPrimaryKey(shopper);
    }

    private void vipNotify(AlipayOrder alipayOrder){
        Long accountId = alipayOrder.getAccountId();
        VipDetail vipDetail = vipDetailMapper.selectByPrimaryKey(alipayOrder.getVipDetailId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //查看客户是否已经是VIP 如果不是则新建 如果是则续费
        CustomerVip customerVip = customerVipMapper.selectByAccountId(accountId);
        if (customerVip == null){
            CustomerVip customerVip1 = new CustomerVip();
            customerVip1.setAccountId(accountId);
            customerVip1.setVipDetailId(alipayOrder.getVipDetailId());
            //计算VIP时间
            Integer timeLong = vipDetail.getTimeLong();
            Calendar calendar = Calendar.getInstance();
            customerVip1.setStartTime(calendar.getTime());
            calendar.add(Calendar.MONTH, timeLong);
            customerVip1.setEndTime(calendar.getTime());

            customerVipMapper.insert(customerVip1);
        }else {
            customerVip.setVipDetailId(vipDetail.getVipDetailId());
            //计算VIP时间
            Calendar calendar = Calendar.getInstance();
            // 续费的开始时间
            calendar.setTime(customerVip.getEndTime());
            Integer timeLong = vipDetail.getTimeLong();
            calendar.add(Calendar.MONTH, timeLong);
            customerVip.setEndTime(calendar.getTime());
            customerVipMapper.updateByPrimaryKey(customerVip);
        }
    }

    @Override
//    public String aliPayNotify(HttpServletRequest request) {
    public String aliPayNotify(Map<String, String> params) {
        // 获取支付宝异步通知的所有参数
//        Map<String, String> params =  AliPayUtils.convertRequestParamsToMap(request);
        try {
            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayProperties.getPublicKey(),AlipayProperties.getCharset(),AlipayProperties.getSignType());

            if(signVerified){
                //校验参数
                this.check(params);
                // 另起线程处理业务逻辑
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        Logger logger = LoggerFactory.getLogger(params.get("out_trade_no") + ".log");
                        //更改订单状态
                        AlipayOrder alipayOrder = alipayOrderMapper.selectByPrimaryKey(params.get("out_trade_no"));
                        alipayOrder.setStatus("success");
                        alipayOrderMapper.updateByPrimaryKey(alipayOrder);
                        logger.debug("开始记录日志");
                        //根据不同的 subject调用不同的处理函数
                        switch (alipayOrder.getSubject()) {
                            case "recharge":
                                rechargeNotify(alipayOrder);
                                break;
                            case "shopping":
                                shoppingNotify(alipayOrder);
                                break;
                            case "vip":
                                vipNotify(alipayOrder);
                                break;
                        }
                    }
                });
                return "success";
            } else {
                return "failure";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "failure";
        }
    }
}


