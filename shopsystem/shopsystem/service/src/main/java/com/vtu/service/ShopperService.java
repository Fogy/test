package com.vtu.service;

import com.vtu.model.Shopper;

import java.util.List;

public interface ShopperService {

    List<Shopper> getAuditingShoppers();
    Shopper getShopperDetails(Long shopperId);
    Integer auditingShopper(Long shopperId, String authenicat);
}
