package com.vtu.model;

public class PreviewUrl {
    /**
     * 预览作品Id
     */
    private Long previewUrlId;

    /**
     * 作品预览Id
     */
    private Long worksId;

    /**
     * 作品预览地址
     */
    private String previewUrl;

    /**
     * 作品预览名称
     */
    private String previewName;

    /**
     * 作品预览大小
     */
    private Double previewSize;

    public Long getPreviewUrlId() {
        return previewUrlId;
    }

    public void setPreviewUrlId(Long previewUrlId) {
        this.previewUrlId = previewUrlId;
    }

    public Long getWorksId() {
        return worksId;
    }

    public void setWorksId(Long worksId) {
        this.worksId = worksId;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl == null ? null : previewUrl.trim();
    }

    public String getPreviewName() {
        return previewName;
    }

    public void setPreviewName(String previewName) {
        this.previewName = previewName;
    }

    public Double getPreviewSize() {
        return previewSize;
    }

    public void setPreviewSize(Double previewSize) {
        this.previewSize = previewSize;
    }
}