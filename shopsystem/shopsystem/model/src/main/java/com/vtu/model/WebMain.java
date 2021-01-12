package com.vtu.model;

public class WebMain {
    private Integer id;

    private Long mainTypeId;

    private Long worksId;

    private String isDisplay;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getMainTypeId() {
        return mainTypeId;
    }

    public void setMainTypeId(Long mainTypeId) {
        this.mainTypeId = mainTypeId;
    }

    public Long getWorksId() {
        return worksId;
    }

    public void setWorksId(Long worksId) {
        this.worksId = worksId;
    }

    public String getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(String isDisplay) {
        this.isDisplay = isDisplay == null ? null : isDisplay.trim();
    }
}