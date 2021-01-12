package com.vtu.model;

public class Discount {
    private Long id;

    private Long shopperId;

    private String discountVip;

    private Double discountRate;

    private String discountDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopperId() {
        return shopperId;
    }

    public void setShopperId(Long shopperId) {
        this.shopperId = shopperId;
    }

    public String getDiscountVip() {
        return discountVip;
    }

    public void setDiscountVip(String discountVip) {
        this.discountVip = discountVip == null ? null : discountVip.trim();
    }

    public Double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Double discountRate) {
        this.discountRate = discountRate;
    }

    public String getDiscountDesc() {
        return discountDesc;
    }

    public void setDiscountDesc(String discountDesc) {
        this.discountDesc = discountDesc == null ? null : discountDesc.trim();
    }
}