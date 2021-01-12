package com.vtu.model;

public class WebMainTypes {
    private Integer mainTypeId;

    private String mainTyeName;

    private String mainTyeDesc;

    public Integer getMainTypeId() {
        return mainTypeId;
    }

    public void setMainTypeId(Integer mainTypeId) {
        this.mainTypeId = mainTypeId;
    }

    public String getMainTyeName() {
        return mainTyeName;
    }

    public void setMainTyeName(String mainTyeName) {
        this.mainTyeName = mainTyeName == null ? null : mainTyeName.trim();
    }

    public String getMainTyeDesc() {
        return mainTyeDesc;
    }

    public void setMainTyeDesc(String mainTyeDesc) {
        this.mainTyeDesc = mainTyeDesc == null ? null : mainTyeDesc.trim();
    }
}