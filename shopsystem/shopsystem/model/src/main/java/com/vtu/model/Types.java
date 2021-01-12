package com.vtu.model;

public class Types {
    private Integer typesId;

    private String typesName;

    private String typesDesc;

    private Integer typesParId;

    public Integer getTypesId() {
        return typesId;
    }

    public void setTypesId(Integer typesId) {
        this.typesId = typesId;
    }

    public String getTypesName() {
        return typesName;
    }

    public void setTypesName(String typesName) {
        this.typesName = typesName == null ? null : typesName.trim();
    }

    public String getTypesDesc() {
        return typesDesc;
    }

    public void setTypesDesc(String typesDesc) {
        this.typesDesc = typesDesc == null ? null : typesDesc.trim();
    }

    public Integer getTypesParId() {
        return typesParId;
    }

    public void setTypesParId(Integer typesParId) {
        this.typesParId = typesParId;
    }
}