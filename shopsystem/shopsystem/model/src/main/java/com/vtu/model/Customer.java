package com.vtu.model;

import java.math.BigDecimal;
import java.util.Date;

public class Customer {
    private Long accountId;

    private String account;

    private String password;

    private String name;

    private String sex;

    private String phone;

    private String idCard;

    private BigDecimal remain;

    private String isVip;

    private String isShoper;

    private String email;

    private String emailSendTime;

    private String wxId;

    private String qqId;

    private String authCode;

    private String isAuth;

    private String headerImage;

    private String idCardUrl;

    //护照
    private String passPort;
    //营业执照
    private String businessLiscence;
    //协议
    private String protocol;
    //商家类型
    private String shoperType;

    //商家状态
    private String shoperStatus;

    public String getShoperStatus() {
        return shoperStatus;
    }

    public void setShoperStatus(String shoperStatus) {
        this.shoperStatus = shoperStatus;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    private CustomerVip customerVip;
    // 阿里云服务器地址，不同环境值不同
    private String domainApp;
    // oss存储头像的地址
    private String headPortraitUrl;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public BigDecimal getRemain() {
        return remain;
    }

    public void setRemain(BigDecimal remain) {
        this.remain = remain;
    }

    public String getIsVip() {
        return isVip;
    }

    public void setIsVip(String isVip) {
        this.isVip = isVip == null ? null : isVip.trim();
    }

    public String getIsShoper() {
        return isShoper;
    }

    public void setIsShoper(String isShoper) {
        this.isShoper = isShoper == null ? null : isShoper.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getQqId() {
        return qqId;
    }

    public void setQqId(String qqId) {
        this.qqId = qqId;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(String isAuth) {
        this.isAuth = isAuth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmailSendTime() {
        return emailSendTime;
    }

    public void setEmailSendTime(String emailSendTime) {
        this.emailSendTime = emailSendTime;
    }

    public CustomerVip getCustomerVip() {
        return customerVip;
    }

    public void setCustomerVip(CustomerVip customerVip) {
        this.customerVip = customerVip;
    }

    public String getHeadPortraitUrl() {
        return headPortraitUrl;
    }

    public void setHeadPortraitUrl(String headPortraitUrl) {
        this.headPortraitUrl = headPortraitUrl;
    }

    public String getDomainApp() {
        return domainApp;
    }

    public void setDomainApp(String domainApp) {
        this.domainApp = domainApp;
    }

    public String getIdCardUrl() {
        return idCardUrl;
    }

    public void setIdCardUrl(String idCardUrl) {
        this.idCardUrl = idCardUrl;
    }

    public String getPassPort() {
        return passPort;
    }

    public void setPassPort(String passPort) {
        this.passPort = passPort;
    }

    public String getBusinessLiscence() {
        return businessLiscence;
    }

    public void setBusinessLiscence(String businessLiscence) {
        this.businessLiscence = businessLiscence;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getShoperType() {
        return shoperType;
    }

    public void setShoperType(String shoperType) {
        this.shoperType = shoperType;
    }
}