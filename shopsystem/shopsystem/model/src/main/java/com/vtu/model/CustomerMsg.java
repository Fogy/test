package com.vtu.model;

public class CustomerMsg {
    private Long customerMsgId;

    private Long accountId;

    private String msgTitle;

    private String msgContent;

    private String isRead;

    public Long getCustomerMsgId() {
        return customerMsgId;
    }

    public void setCustomerMsgId(Long customerMsgId) {
        this.customerMsgId = customerMsgId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle == null ? null : msgTitle.trim();
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent == null ? null : msgContent.trim();
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead == null ? null : isRead.trim();
    }
}