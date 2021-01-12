package com.vtu.web.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.vtu.web.config.AliyunOSSProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AliyunUtils {
    @Autowired
    private AliyunOSSProperties aliyunOSSProperties;
    /**
     * 获取ossClient
     * @return
     */
    public OSS getOssClient() {
        //文件上传
        OSS ossClient = new OSSClientBuilder().build(AliyunOSSProperties.OSS_END_POINT, AliyunOSSProperties.OSS_ACCESS_KEY_ID, AliyunOSSProperties.OSS_ACCESS_KEY_SECRET);
        //容器不存在，就创建
        if (!ossClient.doesBucketExist(AliyunOSSProperties.OSS_BUCKET_NAME)) {
            ossClient.createBucket(AliyunOSSProperties.OSS_BUCKET_NAME);
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(AliyunOSSProperties.OSS_BUCKET_NAME);
            createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
            ossClient.createBucket(createBucketRequest);
        }
        return ossClient;
    }
}
