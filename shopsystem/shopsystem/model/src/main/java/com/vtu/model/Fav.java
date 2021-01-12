package com.vtu.model;

import java.util.Date;

public class Fav {
    private Long favId;

    private Long accountId;

    private Long worksId;

    private Date addTime;

    private Works works;

    private  PreviewUrl previewUrl;

    public Long getFavId() {
        return favId;
    }

    public void setFavId(Long favId) {
        this.favId = favId;
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

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Works getWorks() {
        return works;
    }

    public void setWorks(Works works) {
        this.works = works;
    }

    public PreviewUrl getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(PreviewUrl previewUrl) {
        this.previewUrl = previewUrl;
    }
}