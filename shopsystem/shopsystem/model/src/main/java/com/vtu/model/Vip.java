package com.vtu.model;

import java.util.List;

public class Vip {
    private Long id;

    private Integer vipType;

    private String typeName;

    private List<VipDetail> vipDetailList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVipType() {
        return vipType;
    }

    public void setVipType(Integer vipType) {
        this.vipType = vipType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<VipDetail> getVipDetailList() {
        return vipDetailList;
    }

    public void setVipDetailList(List<VipDetail> vipDetailList) {
        this.vipDetailList = vipDetailList;
    }

}