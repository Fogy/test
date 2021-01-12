package com.vtu.model;

import java.util.Date;

public class ShoppingHis {
    private Long shoppingHisId;

    private Long accountId;

    private Long shoppingId;

    private Date shopingDate;

    public Long getShoppingHisId() {
        return shoppingHisId;
    }

    public void setShoppingHisId(Long shoppingHisId) {
        this.shoppingHisId = shoppingHisId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getShoppingId() {
        return shoppingId;
    }

    public void setShoppingId(Long shoppingId) {
        this.shoppingId = shoppingId;
    }

    public Date getShopingDate() {
        return shopingDate;
    }

    public void setShopingDate(Date shopingDate) {
        this.shopingDate = shopingDate;
    }
}