package com.vtu.model;

import java.util.Date;

public class WorksDiscount {
    private Long id;

    private Long discountId;

    private Long worksId;

    private Date bginTime;

    private Date endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public Long getWorksId() {
        return worksId;
    }

    public void setWorksId(Long worksId) {
        this.worksId = worksId;
    }

    public Date getBginTime() {
        return bginTime;
    }

    public void setBginTime(Date bginTime) {
        this.bginTime = bginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}