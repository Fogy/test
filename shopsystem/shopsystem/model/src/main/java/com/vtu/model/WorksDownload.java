package com.vtu.model;

import java.util.Date;

public class WorksDownload extends Works {
    private Long downId;

    private Long accountId;

    private Long worksId;

    private Date downTime;

    private Works works;

    private  String previewUrl;

    public Long getDownId() {
        return downId;
    }

    public void setDownId(Long downId) {
        this.downId = downId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getWorksId() {
        return worksId;
    }

    public void setWorksId(Long worksId) {
        this.worksId = worksId;
    }

    public Date getDownTime() {
        return downTime;
    }

    public void setDownTime(Date downTime) {
        this.downTime = downTime;
    }

    public Works getWorks() {
        return works;
    }

    public void setWorks(Works works) {
        this.works = works;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }
}