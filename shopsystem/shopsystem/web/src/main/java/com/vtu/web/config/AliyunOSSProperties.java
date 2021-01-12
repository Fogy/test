package com.vtu.web.config;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aliyun-oss")
@Data
public class AliyunOSSProperties implements InitializingBean {

    /**
     * 服务器地点
     */
    private String region;
    /**
     * 服务器地址
     */
    private String endpoint;
    /**
     * OSS身份id
     */
    private String accessKeyId;
    /**
     * 身份密钥
     */
    private String accessKeySecret;

    /**
     * App文件bucketName
     */
    private String bucketApp;
    /**
     * App包文件地址前缀
     */
    private String domainApp;

    /**
     * 自定义的路径
     */
    private String filehost;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketApp() {
        return bucketApp;
    }

    public void setBucketApp(String bucketApp) {
        this.bucketApp = bucketApp;
    }

    public String getDomainApp() {
        return domainApp;
    }

    public void setDomainApp(String domainApp) {
        this.domainApp = domainApp;
    }

    public String getFilehost() {
        return filehost;
    }

    public void setFilehost(String filehost) {
        this.filehost = filehost;
    }

    public static String OSS_END_POINT;
    public static String OSS_ACCESS_KEY_ID;
    public static String OSS_ACCESS_KEY_SECRET;
    public static String OSS_BUCKET_NAME;
    public static String OSS_FILE_HOST;
    public static String OSS_DOM_APP;

    @Override
    public void afterPropertiesSet() throws Exception {
        OSS_END_POINT = endpoint;
        OSS_ACCESS_KEY_ID = accessKeyId;
        OSS_ACCESS_KEY_SECRET = accessKeySecret;
        OSS_BUCKET_NAME = bucketApp;
        OSS_FILE_HOST = filehost;
        OSS_DOM_APP = domainApp;

    }
}
