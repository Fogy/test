package com.vtu.model;

import java.math.BigDecimal;
import java.util.Date;

public class Shopper {
    private Long shopperId;

    private Long accountId;

    private String isAuthenicat;

    private String contractUrl;

    private Date contractBegain;

    private Date contractEnd;

    private String idCard;

    private String idCard1;

    private String realName;

    private String zfbAccount;

    private String identity;

    private String phoneNum;

    private String email;

    private BigDecimal cash;

    public Long getShopperId() {
        return shopperId;
    }

    public void setShopperId(Long shopperId) {
        this.shopperId = shopperId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getIsAuthenicat() {
        return isAuthenicat;
    }

    public void setIsAuthenicat(String isAuthenicat) {
        this.isAuthenicat = isAuthenicat == null ? null : isAuthenicat.trim();
    }

    public String getContractUrl() {
        return contractUrl;
    }

    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl == null ? null : contractUrl.trim();
    }

    public Date getContractBegain() {
        return contractBegain;
    }

    public void setContractBegain(Date contractBegain) {
        this.contractBegain = contractBegain;
    }

    public Date getContractEnd() {
        return contractEnd;
    }

    public void setContractEnd(Date contractEnd) {
        this.contractEnd = contractEnd;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getIdCard1() {
        return idCard1;
    }

    public void setIdCard1(String idCard1) {
        this.idCard1 = idCard1 == null ? null : idCard1.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getZfbAccount() {
        return zfbAccount;
    }

    public void setZfbAccount(String zfbAccount) {
        this.zfbAccount = zfbAccount == null ? null : zfbAccount.trim();
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }
}