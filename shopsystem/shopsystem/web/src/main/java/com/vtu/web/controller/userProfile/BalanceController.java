package com.vtu.web.controller.userProfile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.vtu.model.Customer;
import com.vtu.model.RechargeRecord;
import com.vtu.model.WorksDownload;
import com.vtu.web.service.BalanceService;
import com.vtu.web.service.PersonalBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/balance")
public class BalanceController {

    @Autowired
    PersonalBalanceService personalBalanceService;

    @Autowired
    BalanceService balanceService;
    /**
     * 获取当前用户余额
     * @return
     */
    @RequestMapping(value = "/getUserBalance")
    @ResponseBody
    public BigDecimal getUserBalance (HttpSession session){
        Customer customer = (Customer) session.getAttribute("customer");
        BigDecimal balance = personalBalanceService.getPersonalBalanceByAccountId(customer.getAccountId());
        return  balance;
    }

    /**
     * 用户充值
     * @param session
     * @param payAmount
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/recharge")
    public String recharge(HttpSession session, BigDecimal payAmount){
        Customer customer = (Customer) session.getAttribute("customer");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountId",customer.getAccountId());
        jsonObject.put("payAmount",payAmount);
        return balanceService.recharge(jsonObject);
    }

    /**
     * 获取用户充值记录
     * @param request
     * @param length
     * @param start
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRechargeRecords",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map getRechargeRecords(HttpServletRequest request, int length, int start){
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        PageInfo<RechargeRecord> pageInfo = personalBalanceService.getPersonalBalanceByAccountId(customer.getAccountId(),start+1,length );
        Map<String,Object> map = new HashMap<>();
        map.put("data",pageInfo.getList());
        map.put("recordsFiltered",pageInfo.getTotal());
        map.put("recordsTotal",pageInfo.getSize());

        return map;
    }

}
