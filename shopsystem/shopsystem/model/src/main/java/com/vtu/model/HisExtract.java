package com.vtu.model;

public class HisExtract {
    private Long hisExtractId;

    private Long accountId;

    private String alipayCount;

    private String alipayName;

    private Long manageId;

    private String isSuccess;

    private Long currentRemain;

    private Long extraAmount;

    private Long afterRemain;

    private String errorMsg;

    public Long getHisExtractId() {
        return hisExtractId;
    }

    public void setHisExtractId(Long hisExtractId) {
        this.hisExtractId = hisExtractId;
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

    public Long getManageId() {
        return manageId;
    }

    public void setManageId(Long manageId) {
        this.manageId = manageId;
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess == null ? null : isSuccess.trim();
    }

    public Long getCurrentRemain() {
        return currentRemain;
    }

    public void setCurrentRemain(Long currentRemain) {
        this.currentRemain = currentRemain;
    }

    public Long getExtraAmount() {
        return extraAmount;
    }

    public void setExtraAmount(Long extraAmount) {
        this.extraAmount = extraAmount;
    }

    public Long getAfterRemain() {
        return afterRemain;
    }

    public void setAfterRemain(Long afterRemain) {
        this.afterRemain = afterRemain;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? null : errorMsg.trim();
    }
}