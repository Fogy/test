package com.vtu.model;

public class Manage {
    private Long manageId;

    private String manageCount;

    private String managePassword;

    private String manageName;

    private String manageDesc;

    public Long getManageId() {
        return manageId;
    }

    public void setManageId(Long manageId) {
        this.manageId = manageId;
    }

    public String getManageCount() {
        return manageCount;
    }

    public void setManageCount(String manageCount) {
        this.manageCount = manageCount == null ? null : manageCount.trim();
    }

    public String getManagePassword() {
        return managePassword;
    }

    public void setManagePassword(String managePassword) {
        this.managePassword = managePassword == null ? null : managePassword.trim();
    }

    public String getManageName() {
        return manageName;
    }

    public void setManageName(String manageName) {
        this.manageName = manageName == null ? null : manageName.trim();
    }

    public String getManageDesc() {
        return manageDesc;
    }

    public void setManageDesc(String manageDesc) {
        this.manageDesc = manageDesc == null ? null : manageDesc.trim();
    }
}