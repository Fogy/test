package com.vtu.model;

public class Extract {
    private Long extractId;

    private Long accountId;

    private String alipayCount;

    private String alipayName;

    private Long amount;

    private Long currentRemain;

    public Long getExtractId() {
        return extractId;
    }

    public void setExtractId(Long extractId) {
        this.extractId = extractId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAlipayCount() {
        return alipayCount;
    }

    public void setAlipayCount(String alipayCount) {
        this.alipayCount = alipayCount == null ? null : alipayCount.trim();
    }

    public String getAlipayName() {
        return alipayName;
    }

    public void setAlipayName(String alipayName) {
        this.alipayName = alipayName == null ? null : alipayName.trim();
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getCurrentRemain() {
        return currentRemain;
    }

    public void setCurrentRemain(Long currentRemain) {
        this.currentRemain = currentRemain;
    }
}