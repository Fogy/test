package com.vtu.model;

public class DownTypes {
    private Long id;

    private String downTypes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDownTypes() {
        return downTypes;
    }

    public void setDownTypes(String downTypes) {
        this.downTypes = downTypes == null ? null : downTypes.trim();
    }
}