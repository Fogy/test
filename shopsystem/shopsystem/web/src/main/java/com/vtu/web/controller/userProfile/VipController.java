package com.vtu.web.controller.userProfile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vtu.model.BaseResult;
import com.vtu.model.Customer;
import com.vtu.web.service.VipService;
import com.vtu.web.util.AliPayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;


@Controller
public class VipController {

    @Autowired
    VipService vipService;

    /**
     * 获取vip详情
     * @param vipType
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getVipDetails")
    public BaseResult getVipDetails(Long vipType){
        BaseResult vipDetails = vipService.getVipDetails(vipType);
        return  vipDetails;
    }

    /**
     * 获取VIP
     * @param vipId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getVipById")
    public BaseResult getVipById(Long vipId){
        BaseResult baseResult = vipService.getVipById(vipId);
        return  baseResult;
    }

    /**
     * 购买VIP
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/sellVip")
    public BaseResult sellVip(HttpSession session,Long vipId){
        Customer customer = (Customer) session.getAttribute("customer");
        BaseResult baseResult = vipService.sellVip(vipId,customer.getAccountId());
        return baseResult;
    }

    /**
     * 购买VIP后付款通知
     * @return
     */
    @RequestMapping(value = "/recharVipPay")
    public String recharVipPay(HttpServletRequest request){
        Map<String, String> params =  AliPayUtils.convertRequestParamsToMap(request);
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(params));
        BaseResult baseResult = vipService.recharVipPay(jsonObject);
        return "userProfile/user_profile";
    }

}
