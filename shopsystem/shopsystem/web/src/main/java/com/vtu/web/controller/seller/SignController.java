package com.vtu.web.controller.seller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.vtu.model.BaseResult;
import com.vtu.model.Customer;
import com.vtu.web.config.AliyunOSSProperties;
import com.vtu.web.service.CustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class SignController {
    @Autowired
    CustomerService customerService;

    /**
     * 个人签约申请
     * @param request
     * @return
     */
    @RequestMapping(value = "/personalSign",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult personalSign(HttpServletRequest request){
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        BaseResult result = new BaseResult();
        if(request instanceof MultipartHttpServletRequest){
            MultipartHttpServletRequest mrequest;
            mrequest = (MultipartHttpServletRequest) request;
            MultipartFile idCardUrl = mrequest.getFile("idCardUrl");
            MultipartFile passPort = mrequest.getFile("passPort");
            MultipartFile protocol = mrequest.getFile("protocol");
            customer = customerService.getCustomerById(customer.getAccountId());
            if(StringUtils.isNotBlank(idCardUrl.getOriginalFilename())){
                String carUrl = uploadFilePro(idCardUrl,customer.getIdCardUrl());
                customer.setIdCardUrl(carUrl);
            }
            if(StringUtils.isNotBlank(passPort.getOriginalFilename())){
                String passport = uploadFilePro(passPort,customer.getPassPort());
                customer.setPassPort(passport);
            }
            if(StringUtils.isNotBlank(protocol.getOriginalFilename())){
                String protocolUrl = uploadFilePro(protocol,customer.getProtocol());
                customer.setProtocol(protocolUrl);
            }
            customer.setShoperStatus("auditing");
            customer.setShoperType("personal");
            customer.setIsShoper("true");
            customerService.editCustomer(customer);
            result.setCode(200);
        }
        return result;
    }

    /**
     * 上传认证附件
     * @param headerImage 附件
     * @param url
     * @return
     */
    private String uploadFilePro(MultipartFile headerImage, String url) {
        if(StringUtils.isNotBlank(headerImage.getOriginalFilename())){
            OSS ossClient = getOssClient();
            long times = System.currentTimeMillis();
            String headeImageUrl = "sign"+"/"+"personalSign"+"/"+ times+"/"+"headerImage"+"/"+headerImage.getName();
            if(!StringUtils.isAllEmpty(url)){
                headeImageUrl = url;
            }
            try {
                headeImageUrl = getHeaderImageUrl(ossClient,headerImage,headeImageUrl);
                return headeImageUrl;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
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

    /**
     * 保存文件保存
     * @param ossClient
     * @param UrlFile
     * @param previewUrl
     * @return
     * @throws IOException
     */
    private String getHeaderImageUrl(OSS ossClient, MultipartFile UrlFile, String previewUrl) throws IOException {
        previewUrl = previewUrl.replace(AliyunOSSProperties.OSS_DOM_APP,"");
        if (ossClient.doesObjectExist(AliyunOSSProperties.OSS_BUCKET_NAME,previewUrl)){
            ossClient.deleteObject(AliyunOSSProperties.OSS_BUCKET_NAME,previewUrl);
        }
        previewUrl = previewUrl.substring(0,previewUrl.lastIndexOf("/")+1)+UrlFile.getOriginalFilename();
        ossClient.putObject(new PutObjectRequest(AliyunOSSProperties.OSS_BUCKET_NAME, previewUrl, UrlFile.getInputStream()));
        previewUrl = AliyunOSSProperties.OSS_DOM_APP+previewUrl;
        return previewUrl;
    }

}
