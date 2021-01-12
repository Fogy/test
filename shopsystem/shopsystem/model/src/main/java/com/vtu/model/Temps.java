package com.vtu.model;

import java.math.BigDecimal;

public class Temps {
    private Long tempsId;

    private Long accountId;

    private String tempsTitle;

    private String tempsDesc;

    private String tempsType;

    private BigDecimal price;

    private String keyWord;

    private Integer typesId;

    private String tempsStatus;

    private String channel;

    private String colorsytem;

    private String tempsDuration;

    private String version;

    private String resolution;

    private String tempUrl;

    private String compressUrl;

    private String previewUrl;

    private String synthesisUrl;

    private String zipUrl;

    private String tempsMd5;

    private Double tempsSize;

    public Long getTempsId() {
        return tempsId;
    }

    public void setTempsId(Long tempsId) {
        this.tempsId = tempsId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getTempsTitle() {
        return tempsTitle;
    }

    public void setTempsTitle(String tempsTitle) {
        this.tempsTitle = tempsTitle == null ? null : tempsTitle.trim();
    }

    public String getTempsDesc() {
        return tempsDesc;
    }

    public void setTempsDesc(String tempsDesc) {
        this.tempsDesc = tempsDesc == null ? null : tempsDesc.trim();
    }

    public String getTempsType() {
        return tempsType;
    }

    public void setTempsType(String tempsType) {
        this.tempsType = tempsType == null ? null : tempsType.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord == null ? null : keyWord.trim();
    }

    public Integer getTypesId() {
        return typesId;
    }

    public void setTypesId(Integer typesId) {
        this.typesId = typesId;
    }

    public String getTempsStatus() {
        return tempsStatus;
    }

    public void setTempsStatus(String tempsStatus) {
        this.tempsStatus = tempsStatus == null ? null : tempsStatus.trim();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public String getColorsytem() {
        return colorsytem;
    }

    public void setColorsytem(String colorsytem) {
        this.colorsytem = colorsytem == null ? null : colorsytem.trim();
    }

    public String getTempsDuration() {
        return tempsDuration;
    }

    public void setTempsDuration(String tempsDuration) {
        this.tempsDuration = tempsDuration == null ? null : tempsDuration.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution == null ? null : resolution.trim();
    }

    public String getTempUrl() {
        return tempUrl;
    }

    public void setTempUrl(String tempUrl) {
        this.tempUrl = tempUrl == null ? null : tempUrl.trim();
    }

    public String getCompressUrl() {
        return compressUrl;
    }

    public void setCompressUrl(String compressUrl) {
        this.compressUrl = compressUrl == null ? null : compressUrl.trim();
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl == null ? null : previewUrl.trim();
    }

    public String getSynthesisUrl() {
        return synthesisUrl;
    }

    public void setSynthesisUrl(String synthesisUrl) {
        this.synthesisUrl = synthesisUrl == null ? null : synthesisUrl.trim();
    }

    public String getZipUrl() {
        return zipUrl;
    }

    public void setZipUrl(String zipUrl) {
        this.zipUrl = zipUrl == null ? null : zipUrl.trim();
    }

    public String getTempsMd5() {
        return tempsMd5;
    }

    public void setTempsMd5(String tempsMd5) {
        this.tempsMd5 = tempsMd5 == null ? null : tempsMd5.trim();
    }

    public Double getTempsSize() {
        return tempsSize;
    }

    public void setTempsSize(Double tempsSize) {
        this.tempsSize = tempsSize;
    }
}