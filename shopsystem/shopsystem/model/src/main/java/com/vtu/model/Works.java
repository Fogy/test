package com.vtu.model;

import java.math.BigDecimal;

public class Works {
    private Long worksId;

    private Long accountId;

    private String title;

    private String desc;

    private String type;

    private BigDecimal price;

    private String keyWord;

    private Integer typesId;

    private String status;

    private String channel;

    private String colorsytem;

    private String duration;

    private String version;

    private String resolution;

    private String worksUrl;

    private String md5;

    private String name;

    private Double size;

    private String compressUrl;

    private String previewUrl;

    private String synthesisUrl;

    private String zipUrl;

    private String material;

    private String materialType;

    private String modifyScope;

    private String isDel;

    private String shoperType;

    public String getShoperType() {
        return shoperType;
    }

    public void setShoperType(String shoperType) {
        this.shoperType = shoperType;
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    public Long getWorksId() {
        return worksId;
    }

    public void setWorksId(Long worksId) {
        this.worksId = worksId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration == null ? null : duration.trim();
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

    public String getWorksUrl() {
        return worksUrl;
    }

    public void setWorksUrl(String worksUrl) {
        this.worksUrl = worksUrl == null ? null : worksUrl.trim();
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5 == null ? null : md5.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
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

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getModifyScope() {
        return modifyScope;
    }

    public void setModifyScope(String modifyScope) {
        this.modifyScope = modifyScope;
    }
}