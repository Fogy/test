package com.vtu.model;

import java.util.Date;

public class ShoppingCar {
    private Long shoppingCarId;

    private Long accoundId;

    private Long shoppingId;

    private Date addDate;

    private String worksType;

    public Long getShoppingCarId() {
        return shoppingCarId;
    }

    public void setShoppingCarId(Long shoppingCarId) {
        this.shoppingCarId = shoppingCarId;
    }

    public Long getAccoundId() {
        return accoundId;
    }

    public void setAccoundId(Long accoundId) {
        this.accoundId = accoundId;
    }

    public Long getShoppingId() {
        return shoppingId;
    }

    public void setShoppingId(Long shoppingId) {
        this.shoppingId = shoppingId;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public String getWorksType() {
        return worksType;
    }

    public void setWorksType(String worksType) {
        this.worksType = worksType;
    }
}