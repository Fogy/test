package com.vtu.web.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    /**
     * 用户更改头像
     * @param request
     * @return
     */
    @RequestMapping(value = "/changeHeaderImage")
    public BaseResult changeHeaderImage(HttpServletRequest request){
        BaseResult baseResult  = new BaseResult();
        MultipartHttpServletRequest mrequest;
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        customer = customerService.getCustomerById(customer.getAccountId());
        if(request instanceof MultipartHttpServletRequest){
            mrequest = (MultipartHttpServletRequest) request;
            MultipartFile headerImage = mrequest.getFile("headerImage");
            if(StringUtils.isNotBlank(headerImage.getOriginalFilename())){
                OSS ossClient = getOssClient();
                long times = System.currentTimeMillis();
                String headeImageUrl = "temps"+"/"+"tempOutput"+"/"+ times+"/"+"headerImage"+"/"+headerImage.getName();
                if(!StringUtils.isAllEmpty(customer.getHeaderImage())){
                    headeImageUrl = customer.getHeaderImage();
                }
                    try {
                        headeImageUrl = getHeaderImageUrl(ossClient,headerImage,headeImageUrl);
                        customer.setHeaderImage(headeImageUrl);
                        customerService.editCustomer(customer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
        request.getSession().setAttribute("customer",customer);
        return baseResult;
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


    /**
     * 获取用户登陆信息
     * @param session
     * @return
     */
    @RequestMapping(value = "/getCustomer")
    @ResponseBody
    public BaseResult getUserInfo(HttpSession session){
        Customer customer;
        BaseResult baseResult = new BaseResult();
        if(session != null){
            customer = (Customer) session.getAttribute("customer");
            customer = customerService.getCustomerById(customer.getAccountId());
            baseResult.setCode(200);
            baseResult.setValue(customer);
        }else{
            baseResult.setCode(300);
            baseResult.setMsg("no login");
        }
        return baseResult;

    }
}
