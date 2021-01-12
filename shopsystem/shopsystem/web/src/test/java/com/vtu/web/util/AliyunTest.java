package com.vtu.web.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.vtu.web.config.AliyunOSSProperties;
import com.vtu.web.config.AliyunOSSUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AliyunTest {

    @Autowired
    private AliyunOSSProperties aliyunOSSProperties;

    @Test
    public void testUpload(){
        String fileName = AliyunOSSUtil.upload(new File("F:\\vtuweb\\videoOutput\\1576676664674\\synthesis\\惊悚骷髅.png"));
        System.out.println(fileName);
    }

    @Test
    public void testDel(){
        String path = "video/videoImport/1576950409907/e50b712c1b4a4c71b7c81e8dea10e3e1.mp4";
        //文件上传
        //OSSClient ossClient = new OSSClient(AliyunOSSProperties.OSS_END_POINT, AliyunOSSProperties.OSS_ACCESS_KEY_ID, AliyunOSSProperties.OSS_ACCESS_KEY_SECRET);
        //ossClient.deleteObject(AliyunOSSProperties.OSS_BUCKET_NAME,"videoImport/1576950409907/");
        OSS ossClient = new OSSClientBuilder().build(AliyunOSSProperties.OSS_END_POINT, AliyunOSSProperties.OSS_ACCESS_KEY_ID, AliyunOSSProperties.OSS_ACCESS_KEY_SECRET);
        boolean objectExist = ossClient.doesObjectExist(AliyunOSSProperties.OSS_BUCKET_NAME,path );
        System.out.println("当前文件："+objectExist);
        if (objectExist){
            ossClient.deleteObject(AliyunOSSProperties.OSS_BUCKET_NAME,path);
        }
        ossClient.shutdown();
    }
}
