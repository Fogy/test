package com.vtu.model;

import java.math.BigDecimal;

public class VipDetail {
    private Long vipDetailId;

    private Long vipId;

    private String vipName;

    private BigDecimal prices;

    private Integer timeLong;

    private String timeUnit;

    private Integer comMaterial;

    private Integer material;

    private Integer modialMaterial;

    private Double customDiscount;

    private String invoiceType;

    private Integer sortNum;

    public Long getVipDetailId() {
        return vipDetailId;
    }

    public void setVipDetailId(Long vipDetailId) {
        this.vipDetailId = vipDetailId;
    }

    public Long getVipId() {
        return vipId;
    }

    public void setVipId(Long vipId) {
        this.vipId = vipId;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName == null ? null : vipName.trim();
    }

    public BigDecimal getPrices() {
        return prices;
    }

    public void setPrices(BigDecimal prices) {
        this.prices = prices;
    }

    public Integer getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(Integer timeLong) {
        this.timeLong = timeLong;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit == null ? null : timeUnit.trim();
    }

    public Integer getComMaterial() {
        return comMaterial;
    }

    public void setComMaterial(Integer comMaterial) {
        this.comMaterial = comMaterial;
    }

    public Integer getMaterial() {
        return material;
    }

    public void setMaterial(Integer material) {
        this.material = material;
    }

    public Integer getModialMaterial() {
        return modialMaterial;
    }

    public void setModialMaterial(Integer modialMaterial) {
        this.modialMaterial = modialMaterial;
    }

    public Double getCustomDiscount() {
        return customDiscount;
    }

    public void setCustomDiscount(Double customDiscount) {
        this.customDiscount = customDiscount;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType == null ? null : invoiceType.trim();
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }
}