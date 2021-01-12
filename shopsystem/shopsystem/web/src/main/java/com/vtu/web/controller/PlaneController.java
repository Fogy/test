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
import com.vtu.web.util.MediaUtil;
import com.vtu.web.util.VideoMetaInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
public class PlaneController {

    private static final Logger logger = LoggerFactory.getLogger(TempController.class);

    @Autowired
    private AliyunOSSProperties aliyunOSSProperties;

    @Autowired
    WorksService worksService;

    @Value("${video.videoImport}")
    private String videoImport;

    @Value("${video.videoOutput}")
    private String videoOutput;

    @Value("${video.videoLog}")
    private String videoLog;

    /**
     * 上传视频作品
     * @param request
     * @return
     */
    @RequestMapping(value = "/uploadPlane",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult uploadPlane(HttpServletRequest request){
        MultipartHttpServletRequest mrequest;
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        BaseResult baseResult = new BaseResult();
        if(request instanceof MultipartHttpServletRequest){
            mrequest = (MultipartHttpServletRequest) request;
            baseResult = getBaseResult(mrequest,customer);
        } else {
            baseResult.setCode(500);
            baseResult.setMsg("请选择文件");
        }
        return baseResult;
    }

    //上传你保存作品
    private BaseResult getBaseResult(MultipartHttpServletRequest mrequest, Customer customer) {
        List<MultipartFile> listPlanes = mrequest.getFiles("plane");
        MultipartFile synthesis = listPlanes.get(0);
        OSS ossClient = getOssClient();
        Works works = new Works();
        boolean isPicture = false;
        long times = System.currentTimeMillis();
        //文件名称
        //预览文件名称
        String videoName= synthesis.getOriginalFilename();
        String workType = videoName.substring(videoName.indexOf("."),videoName.length());
        if(workType.equalsIgnoreCase(".PNG") || workType.equalsIgnoreCase(".JPG") ||workType.equalsIgnoreCase(".JIF")){
            isPicture=true;
        }
        //uuid文件名称
        String uuIdFileNme = UUID.randomUUID().toString().replace("-", "")+workType;
        //输入输出总路径
        String inPath = videoImport+ File.separator+times;
        String outPath = videoOutput+File.separator+times;
        String videoPath=inPath+File.separator+uuIdFileNme;
        //原视频文件
        File videoFile = new File(videoPath);
        //预览图片文件夹
        // File prevFile = new File(outPath+File.separator+"preview");
        //合成图片的路径
        String fileName = videoName.substring(0,videoName.indexOf("."));
        String synthesisPath = outPath+File.separator+"synthesis";
        File synthesisFile = new File(synthesisPath+File.separator+fileName+".jpg");
        //Log路径
        File logFile = new File(videoLog);

        //创建上传及生成路径
        createVidoPath(videoFile,  synthesisFile);
        try {
            FileUtils.writeByteArrayToFile(videoFile,synthesis.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(isPicture){
            //合成图片
            MediaUtil.markImageByIcon(videoFile,logFile,synthesisFile);
            String synthesisVideoUrl = "temps"+"/"+"tempOutput"+"/"+ times+"/"+"synthesis"+"/"+synthesisFile.getName();
            works.setPreviewUrl(AliyunOSSProperties.OSS_DOM_APP+synthesisVideoUrl);
            upLoadOSS(synthesisVideoUrl, synthesisFile, ossClient);
        }
        //获取ossClient
        //上传原始视频
        String videoUrl="temps"+"/"+"tempImport"+"/"+ times+"/"+videoFile.getName();
        //上传合成视频

        //上传预览图片
        try {
            works.setMd5(DigestUtils.md5DigestAsHex(synthesis.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        works.setSize((double)synthesis.getSize());
        upLoadOSS(videoUrl, videoFile, ossClient);
        ossClient.shutdown();
        //存贮视频
        works.setWorksUrl(aliyunOSSProperties.getDomainApp()+videoUrl);
        works.setTitle(synthesis.getOriginalFilename());
        works.setVersion(videoName.substring(videoName.lastIndexOf(".")+1));
        works.setName(synthesis.getOriginalFilename());
        works.setStatus("refer");
        works.setType("3");
        //此处要修改
        works.setAccountId(customer.getAccountId());
        //视频时长
        BaseResult baseResult = worksService.insertWorks(works);
        if(null == baseResult && baseResult.getCode()==500){
            baseResult.setMsg("保存文件错误");
        }else{
            baseResult.setCode(200);
            baseResult.setMsg("");
        }
        return baseResult;
    }

    /**
     * OSS上传文件
     * @param filePath
     * @param file
     * @param ossClient
     */
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
     * 创建上传及生成路径
     * @param videoFile
     * @param synthesisFile
     */
    private void createVidoPath(File videoFile, File synthesisFile) {
        //创建视频文件夹
        if(!videoFile.getParentFile().exists()){
            videoFile.getParentFile().mkdirs();
        }
        //创建合成图片路径
        if(!synthesisFile.getParentFile().exists()){
            synthesisFile.getParentFile().mkdirs();
        }
    }

    /**
     *  @param videoFile
     * @param prevFile
     * @param synthesisFile
     * @param compressFile
     * @param logFile
     * @param videoMetaInfo
     */
    private void processVideo(File videoFile, File prevFile, File synthesisFile, File compressFile, File logFile, VideoMetaInfo videoMetaInfo) {
        MediaUtil.getPreviewVido2(videoFile,logFile,compressFile,videoMetaInfo);
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
     * 保存更新
     * @param request
     * @return
     */
    @RequestMapping(value = "/saveUpPlane")
    @ResponseBody
    public BaseResult saveUpPlane(HttpServletRequest request){
        MultipartHttpServletRequest mrequest;
        BaseResult baseResult = null;
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        String worksId = request.getParameter("worksId");
        Works works = worksService.getWorksById(worksId);
        String previewUrl = works.getPreviewUrl();
        String synthesisUrl = works.getSynthesisUrl();
        if(request instanceof MultipartHttpServletRequest){
            mrequest = (MultipartHttpServletRequest) request;
            MultipartFile previewUrlFile = mrequest.getFile("previewUrl");
            MultipartFile synthesisUrlFile = mrequest.getFile("synthesisUrl");
            OSS ossClient = getOssClient();
            try {
                if(null !=previewUrlFile &&  StringUtils.isNotBlank(previewUrlFile.getOriginalFilename())){
                    previewUrl = getPreviewUrl(ossClient,previewUrlFile,previewUrl);
                }
                if(null !=synthesisUrlFile &&  StringUtils.isNotBlank(synthesisUrlFile.getOriginalFilename())){
                    synthesisUrl = getPreviewUrl(ossClient,synthesisUrlFile,synthesisUrl);
                }
            } catch (IOException e) {
                baseResult.setCode(300);
                baseResult.setMsg("上传文件失败");
            }
            ossClient.shutdown();
        }
        //更新作品
        /*worksUrl.setWorksName(request.getParameter("worksName"));*/
        works.setPreviewUrl(previewUrl);
        works.setSynthesisUrl(synthesisUrl);
        works.setChannel(request.getParameter("channel"));

        works.setDesc(request.getParameter("worksDesc"));
        if(StringUtils.isNotBlank(request.getParameter("price"))){
            works.setPrice(new BigDecimal(request.getParameter("price")));
        }
        works.setStatus("auditing");
        works.setTitle(request.getParameter("title"));
        works.setModifyScope(request.getParameter("modifyScope"));
        works.setKeyWord(request.getParameter("keyWord"));
        works.setColorsytem(request.getParameter("colorSytem"));
        works.setChannel(request.getParameter("channel"));
        works.setMaterial(request.getParameter("version"));
        //works.setWorksUrl(worksUrl);
        works.setResolution(request.getParameter("resolution"));
        works.setMaterial(request.getParameter("material"));
        works.setMaterialType(request.getParameter("materialType"));
        works.setShoperType(customer.getShoperType());

        baseResult = worksService.updateWorks(works);


        return baseResult;
    }

    /**
     * 保存文件保存
     * @param ossClient
     * @param UrlFile
     * @param previewUrl
     * @return
     * @throws IOException
     */
    private String getPreviewUrl(OSS ossClient, MultipartFile UrlFile, String previewUrl) throws IOException {
        if(StringUtils.isNotEmpty(previewUrl)){
            previewUrl = previewUrl.replace(AliyunOSSProperties.OSS_DOM_APP,"");
            if (ossClient.doesObjectExist(AliyunOSSProperties.OSS_BUCKET_NAME,previewUrl)){
                ossClient.deleteObject(AliyunOSSProperties.OSS_BUCKET_NAME,previewUrl);
            }
            previewUrl = previewUrl.substring(0,previewUrl.lastIndexOf("/")+1)+UrlFile.getOriginalFilename();
            //ossClient.putObject(new PutObjectRequest(AliyunOSSProperties.OSS_BUCKET_NAME, previewUrl, UrlFile.getInputStream()));

        }else{
            previewUrl = "video"+"/"+"videoOutput"+"/"+ System.currentTimeMillis()+"/"+"preview"+"/"+UrlFile.getOriginalFilename();
        }




        long times = System.currentTimeMillis();
        //文件名称
        String originalName= UrlFile.getOriginalFilename();
        String uuIdFileNme = UUID.randomUUID().toString().replace("-", "")+originalName.substring(originalName.indexOf("."),originalName.length());

        //输入输出总路径
        String inPath = videoImport+ File.separator+times;
        String outPath = videoOutput+File.separator+times;
        String videoPath=inPath+File.separator+uuIdFileNme;
        //原视频文件
        File videoFile = new File(videoPath);
        //预览图片文件夹
        // File prevFile = new File(outPath+File.separator+"preview");
        //合成图片的路径
        String fileName = originalName.substring(0,originalName.indexOf("."));
        String synthesisPath = outPath+File.separator+"synthesis";
        File synthesisFile = new File(synthesisPath+File.separator+fileName+".jpg");
        //Log路径
        File logFile = new File(videoLog);

        //创建上传及生成路径
        createVidoPath(videoFile,  synthesisFile);
        try {
            FileUtils.writeByteArrayToFile(videoFile,UrlFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String videoName= UrlFile.getOriginalFilename();
        String workType = videoName.substring(videoName.indexOf("."),videoName.length());
        boolean isPicture=false;
        if(workType.equalsIgnoreCase(".PNG") || workType.equalsIgnoreCase(".JPG") ||workType.equalsIgnoreCase(".JIF")){
            isPicture=true;
        }
        if(isPicture){
            //合成图片
            MediaUtil.markImageByIcon(videoFile,logFile,synthesisFile);
           // String synthesisVideoUrl = "temps"+"/"+"tempOutput"+"/"+ times+"/"+"synthesis"+"/"+synthesisFile.getName();
            //works.setPreviewUrl(AliyunOSSProperties.OSS_DOM_APP+synthesisVideoUrl);
            upLoadOSS(previewUrl, synthesisFile, ossClient);
        }








        previewUrl = AliyunOSSProperties.OSS_DOM_APP+previewUrl;
        return previewUrl;
    }


}
