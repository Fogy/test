package com.vtu.web.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.vtu.model.BaseResult;
import com.vtu.model.Customer;
import com.vtu.model.Works;
import com.vtu.web.config.AliyunOSSProperties;
import com.vtu.web.service.WorksService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 平面文件
 */
@RestController
public class FlatController {
    private static final  Logger logger= LoggerFactory.getLogger(FlatController.class);
    @Autowired
    private AliyunOSSProperties aliyunOSSProperties;

    @Value("${video.videoImport}")
    private String videoImport;

    @Value("${video.videoOutput}")
    private String videoOutput;

    @Value("${video.videoLog}")
    private String videoLog;

    @Autowired
    WorksService worksService;

    /**
     * 上传平面文件
     * @return
     */
    @RequestMapping(value = "/uploadFlat",method = RequestMethod.POST)
    public BaseResult uploadFlat(HttpServletRequest request){
        MultipartHttpServletRequest mrequest;
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        BaseResult baseResult = new BaseResult();
        if(request instanceof MultipartHttpServletRequest){
            mrequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> videos = mrequest.getFiles("flat");
            for(MultipartFile video:videos){
                baseResult = getBaseResult(video,customer);
                if(baseResult.getCode()==500){
                    return baseResult;
                }
            }
        }else{
            baseResult.setCode(500);
            baseResult.setMsg("请选择文件");
        }
        return baseResult;
    }

    /**
     * 获取上传结果
     * @param video
     * @param customer
     * @return
     */
    private BaseResult getBaseResult(MultipartFile video, Customer customer) {
        BaseResult baseResult = new BaseResult();
        Works works = new Works();
        long times = System.currentTimeMillis();
        //文件名称
        String originalName= video.getOriginalFilename();
        //uuid文件名称
        String uuIdFileNme = UUID.randomUUID().toString().replace("-", "")+originalName.substring(originalName.indexOf("."),originalName.length());
        //输入输出总路径
        String inPath = videoImport+ File.separator+times;
        String videoPath=inPath+File.separator+uuIdFileNme;
        File videoFile = new File(videoPath);
        //创建视频文件夹
        if(!videoFile.getParentFile().exists()){
            videoFile.getParentFile().mkdirs();
        }
        try {
            FileUtils.writeByteArrayToFile(videoFile,video.getBytes());
        } catch (IOException e) {
            baseResult.setCode(500);
            baseResult.setMsg("上传文件错误,请检查视频文件");
            logger.error("上传文件错误{}",e);
            e.printStackTrace();
        }
        String videoUrl="video"+"/"+"videoImport"+"/"+ times+"/"+videoFile.getName();
        works.setWorksUrl(aliyunOSSProperties.getDomainApp()+videoUrl);
        OSS ossClient = getOssClient();
        upLoadOSS(videoUrl, videoFile, ossClient);
        ossClient.shutdown();
        //存贮视频
        works.setWorksUrl(aliyunOSSProperties.getDomainApp()+videoUrl);
        works.setName(originalName);
        works.setTitle(originalName);
        works.setStatus("refer");
        works.setType("3");
        try {
            works.setMd5(DigestUtils.md5DigestAsHex(video.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        works.setSize((double)video.getSize());
        //此处要修改
        works.setAccountId(customer.getAccountId());
        //视频时长
        baseResult = worksService.insertWorks(works);

        //baseResult = worksService.insertWorks(works);
        if(null == baseResult && baseResult.getCode()==500){
            baseResult.setMsg("保存文件错误");
        }else{
            baseResult.setCode(200);
            baseResult.setMsg("");
        }
        return baseResult;

    }

    private void upLoadOSS(String filePath, File file, OSS ossClient) {
        logger.info("阿里云开始上传{}",file.getName());

        //上传文件
        PutObjectResult videoResult = ossClient.putObject(new PutObjectRequest(AliyunOSSProperties.OSS_BUCKET_NAME, filePath, file));
        //判断上传是否成功
        if (null != videoResult) {
            logger.info(file.getName()+":上传成功");
        }
        logger.info("阿里云开始上传{}",filePath);
    }

    /**
     * 获取ossClient
     * @return
     */
    private OSS getOssClient() {
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
