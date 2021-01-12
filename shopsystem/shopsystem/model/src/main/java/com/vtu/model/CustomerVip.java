package com.vtu.model;

import java.util.Date;

public class CustomerVip {
    private Long id;

    private Long accountId;

    private Long vipDetailId;

    private Date startTime;

    private Date endTime;

    private String isVip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getVipDetailId() {
        return vipDetailId;
    }

    public void setVipDetailId(Long vipDetailId) {
        this.vipDetailId = vipDetailId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getIsVip() {
        return isVip;
    }

    public void setIsVip(String isVip) {
        this.isVip = isVip == null ? null : isVip.trim();
    }
}