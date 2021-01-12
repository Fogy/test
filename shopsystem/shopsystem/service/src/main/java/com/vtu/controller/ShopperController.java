package com.vtu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vtu.model.Shopper;
import com.vtu.service.impl.ShopperServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShopperController {
    @Autowired
    ShopperServiceImpl shopperService;

    /**
     * 获取所有认证状态是auditting的店铺，即需要审核的店铺
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/get/auditing/shoppers")
    public PageInfo getAuditingShoppers(@RequestParam("pageNum") Integer pageNum,
                                        @RequestParam("pageSize") Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Shopper> shopperList = shopperService.getAuditingShoppers();
        PageInfo pageInfo = new PageInfo(shopperList);
        return pageInfo;
    }


    /**
     * 根据店铺ID获取店铺的详细信息
     * @param shopperId
     * @return
     */
    @RequestMapping(value = "/get/shopper/details")
    public Shopper getShoperDetails(@RequestParam("shopperId") Long shopperId){
        return shopperService.getShopperDetails(shopperId);
    }

    /**
     * 审核店铺
     * @param shopperId
     * @param authenicat
     * @return
     */
    @RequestMapping(value = "/auditing/shopper")
    public Integer auditingShopper(@RequestParam("shopperId") Long shopperId,
                                   @RequestParam("authenicat") String authenicat){
        return shopperService.auditingShopper(shopperId, authenicat);

    }

}
