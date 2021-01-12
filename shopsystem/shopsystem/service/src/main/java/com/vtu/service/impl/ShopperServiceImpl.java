package com.vtu.service.impl;

import com.vtu.mapper.ShopperMapper;
import com.vtu.model.Shopper;
import com.vtu.service.ShopperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ShopperServiceImpl implements ShopperService {
    @Autowired
    ShopperMapper shopperMapper;
    @Override
    public List<Shopper> getAuditingShoppers() {
        return shopperMapper.selectByAuthenicat("auditing");
    }

    @Override
    public Shopper getShopperDetails(Long shopperId) {
        return shopperMapper.selectByPrimaryKey(shopperId);
    }

    @Override
    public Integer auditingShopper(Long shopperId, String authenicat) {
        Shopper shopper = shopperMapper.selectByPrimaryKey(shopperId);
        shopper.setIsAuthenicat(authenicat);
        return shopperMapper.updateByPrimaryKeySelective(shopper);
    }


}
