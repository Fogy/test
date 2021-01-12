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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
public class WorksController {

    private static Logger logger = LoggerFactory.getLogger(SellerController.class);

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
     * 保存更新
     * @param request
     * @return
     */
    @RequestMapping(value = "/saveUpWorks")
    @ResponseBody
    public BaseResult saveUpWorks(HttpServletRequest request){
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        MultipartHttpServletRequest mrequest;
        BaseResult baseResult = null;
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

        }else{
            previewUrl = "video"+"/"+"videoOutput"+"/"+ System.currentTimeMillis()+"/"+"preview"+"/"+UrlFile.getOriginalFilename();
        }

        ossClient.putObject(new PutObjectRequest(AliyunOSSProperties.OSS_BUCKET_NAME, previewUrl, UrlFile.getInputStream()));
        previewUrl = AliyunOSSProperties.OSS_DOM_APP+previewUrl;
        return previewUrl;
    }

    /**
     * 通过作品Id获取作品
     * @param worksIds
     * @return
     */
    @RequestMapping(value = "/getWorksById")
    @ResponseBody
    public Works getWorksById(String worksIds){
        Works works = worksService.getWorksById(worksIds);
        return works;
    }


    /**
     * 根据用户Id删除作品
     * @param works
     * @return
     */
    @RequestMapping(value = "/getWorksByAccountId",method = RequestMethod.POST )
    @ResponseBody
    public List<Works> getWorksByAccountId(HttpSession session, @RequestBody Works works){
        Customer customer = (Customer) session.getAttribute("customer");
        works.setAccountId(customer.getAccountId());
       return worksService.getWorksByAccountId(works);
    }

    /**
     * 根据作品Id删除作品
     * @param worksIds
     * @return
     */
    @RequestMapping(value = "/delWorksById",method = RequestMethod.POST )
    @ResponseBody
    public BaseResult delWorksById(String worksIds){
        Works works = worksService.getWorksById(worksIds);
        OSS ossClient = getOssClient();
       // WorksUrl worksUrls = works.getWorksUrls();
        String worksUrl = works.getWorksUrl();
        String previewUrl = works.getPreviewUrl();
        String compressUrl = works.getCompressUrl();
        String synthesisUrl = works.getSynthesisUrl();


        if (StringUtils.isNotBlank(worksUrl)){
            worksUrl = worksUrl.replace(AliyunOSSProperties.OSS_DOM_APP,"");
            if (ossClient.doesObjectExist(AliyunOSSProperties.OSS_BUCKET_NAME,worksUrl)){
                ossClient.deleteObject(AliyunOSSProperties.OSS_BUCKET_NAME,worksUrl);
            }
        }

        if(StringUtils.isNotBlank(previewUrl)){
            previewUrl = previewUrl.replace(AliyunOSSProperties.OSS_DOM_APP,"");
            if (ossClient.doesObjectExist(AliyunOSSProperties.OSS_BUCKET_NAME,previewUrl)){
                ossClient.deleteObject(AliyunOSSProperties.OSS_BUCKET_NAME,previewUrl);
            }
        }

        if(StringUtils.isNotBlank(compressUrl)){
            compressUrl = compressUrl.replace(AliyunOSSProperties.OSS_DOM_APP,"");
            if (ossClient.doesObjectExist(AliyunOSSProperties.OSS_BUCKET_NAME,compressUrl)){
                ossClient.deleteObject(AliyunOSSProperties.OSS_BUCKET_NAME,compressUrl);
            }
        }

        if(StringUtils.isNotBlank(compressUrl)){
            synthesisUrl = synthesisUrl.replace(AliyunOSSProperties.OSS_DOM_APP,"");
            if (ossClient.doesObjectExist(AliyunOSSProperties.OSS_BUCKET_NAME,synthesisUrl)){
                ossClient.deleteObject(AliyunOSSProperties.OSS_BUCKET_NAME,synthesisUrl);
            }
        }

        ossClient.shutdown();
        return worksService.delWorksById(worksIds);
    }


    /**
     * 上传视频作品
     * @param request
     * @return
     */
    @RequestMapping(value = "/uploadVideo",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult uploadVideo(HttpServletRequest request){
        MultipartHttpServletRequest mrequest;
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        BaseResult baseResult = new BaseResult();
        if(request instanceof MultipartHttpServletRequest){
            mrequest = (MultipartHttpServletRequest) request;
            List<MultipartFile>  videos = mrequest.getFiles("video");
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

    private BaseResult getBaseResult(MultipartFile video, Customer customer) {
        BaseResult baseResult = new BaseResult();
        Works works = new Works();
       // WorksUrl worksUrl = new WorksUrl();
        long times = System.currentTimeMillis();
        //文件名称
        String originalName= video.getOriginalFilename();
        //uuid文件名称
        String uuIdFileNme = UUID.randomUUID().toString().replace("-", "")+originalName.substring(originalName.indexOf("."),originalName.length());
        //输入输出总路径
        String inPath = videoImport+ File.separator+times;
        String outPath = videoOutput+File.separator+times;

        String videoPath=inPath+File.separator+uuIdFileNme;
        //原视频文件
        File videoFile = new File(videoPath);
        //预览图片文件夹
        File prevFile = new File(outPath+File.separator+"preview");
        //合成图片的路径
        String fileName = originalName.substring(0,originalName.indexOf("."));
        String synthesisPath = outPath+File.separator+"synthesis";
        File synthesisFile = new File(synthesisPath+File.separator+fileName+".jpg");

        //截取视频
        String compressPath = outPath+File.separator+"compress";
        File compressFile = new File(compressPath+File.separator+fileName+".mp4");
        //Log路径
        File logFile = new File(videoLog);
        //创建上传及生成路径
        createVidoPath(videoFile, prevFile, synthesisFile, compressFile);
        try {
            FileUtils.writeByteArrayToFile(videoFile,video.getBytes());
        } catch (IOException e) {
            baseResult.setCode(500);
            baseResult.setMsg("上传文件错误,请检查视频文件");
            logger.error("上传文件错误{}",e);
            e.printStackTrace();
        }
        //获取视频信息
        VideoMetaInfo videoMetaInfo = MediaUtil.getVideoMetaInfo(videoFile);

        if(null == videoMetaInfo){
            baseResult.setCode(500);
            baseResult.setMsg("所选文件存在未知错误,请重新选择。");
            videoFile.deleteOnExit();
        }

        processVideo(videoFile, prevFile, synthesisFile, compressFile, logFile,videoMetaInfo);

        if(!compressFile.exists()){
            baseResult.setCode(500);
            baseResult.setMsg(originalName+"上传文件错误,请检查视频文件");
        }
        //获取ossClient
        OSS ossClient = getOssClient();
        //上传压缩视频
        String videoUrl="video"+"/"+"videoImport"+"/"+ times+"/"+videoFile.getName();
        works.setWorksUrl(AliyunOSSProperties.OSS_DOM_APP+videoUrl);
        //上传压缩视频
        String compressVideoUrl = "video"+"/"+"videoOutput"+"/"+ times+"/"+"compress"+"/"+originalName;
        //worksUrl.setCompressUrl(AliyunOSSProperties.OSS_DOM_APP+compressVideoUrl);
        works.setCompressUrl(AliyunOSSProperties.OSS_DOM_APP+compressVideoUrl);
        //上传合成视频
        String synthesisVideoUrl = "video"+"/"+"videoOutput"+"/"+ times+"/"+"synthesis"+"/"+synthesisFile.getName();
        //worksUrl.setSynthesisUrl(AliyunOSSProperties.OSS_DOM_APP+synthesisVideoUrl);
        works.setSynthesisUrl(AliyunOSSProperties.OSS_DOM_APP+synthesisVideoUrl);
        //上传预览图片
        File[] prevFiles=prevFile.listFiles();
        File prev = prevFiles[prevFiles.length-1];
        String prevUrl = "video"+"/"+"videoOutput"+"/"+ times+"/"+"preview"+"/"+prev.getName();
        //worksUrl.setPreviewUrl(AliyunOSSProperties.OSS_DOM_APP+prevUrl);
        works.setPreviewUrl(AliyunOSSProperties.OSS_DOM_APP+prevUrl);
        try {
            //worksUrl.setWorksMd5(DigestUtils.md5DigestAsHex(video.getInputStream()));
            works.setMd5(DigestUtils.md5DigestAsHex(video.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        works.setSize((double)video.getSize());
        //worksUrl.setWorksName(video.getOriginalFilename());
        upLoadOSS(videoUrl, videoFile, ossClient);
        upLoadOSS(compressVideoUrl, compressFile, ossClient);
        upLoadOSS(synthesisVideoUrl, synthesisFile, ossClient);
        upLoadOSS(prevUrl, prev, ossClient);
        ossClient.shutdown();
        //存贮视频
        works.setTitle(video.getOriginalFilename());
        works.setStatus("refer");
        works.setType("1");
        //此处要修改
        works.setAccountId(customer.getAccountId());
        //视频时长
        double duration = Math.ceil(videoMetaInfo.getDuration()/1000);

        works.setDuration(videoMetaInfo.getDurinfo());
        works.setVersion(originalName.substring(originalName.lastIndexOf(".")+1));

        baseResult = worksService.insertWorks(works);
        if(null == baseResult && baseResult.getCode()==500){
            baseResult.setMsg("保存文件错误");
        }else{
            baseResult.setCode(200);
            baseResult.setMsg("");
        }
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
     *  @param videoFile
     * @param synthesisFile
     * @param prevFile
     * @param compressFile
     * @param logFile
     * @param videoMetaInfo
     */
    private void processVideo(File videoFile, File prevFile, File synthesisFile, File compressFile, File logFile, VideoMetaInfo videoMetaInfo) {
        //视频处理

        //视频时长
        double duration = Math.floor(videoMetaInfo.getDuration()/1000);
        //视频长度
        double videoLength = duration>10?10:duration;
        //生成预览视频
        //MediaUtil.getPreviewVido(videoFile,logFile,"1000k",String.valueOf(videoLength),compressFile);
        MediaUtil.getPreviewVido2(videoFile,logFile,compressFile,videoMetaInfo);
        //生成预览图片
        double frameNum = Math.floor(duration*videoMetaInfo.getFrameRate());
        MediaUtil.getPreview(videoFile,prevFile,Math.floor(frameNum/5),frameNum,Math.floor(frameNum/5));
        try {
            MediaUtil.synthesisPicture(prevFile,synthesisFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建上传及生成路径
     * @param videoFile
     * @param prevFile
     * @param synthesisFile
     * @param compressFile
     */
    private void createVidoPath(File videoFile, File prevFile, File synthesisFile, File compressFile) {
        //创建视频文件夹
        if(!videoFile.getParentFile().exists()){
            videoFile.getParentFile().mkdirs();
        }
        //创建图片文件夹
        if(!prevFile.exists()){
            prevFile.mkdirs();
        }
        //创建视频文件夹
        if(!compressFile.getParentFile().exists()){
            compressFile.getParentFile().mkdirs();
        }
        //创建合成图片路径
        if(!synthesisFile.getParentFile().exists()){
            synthesisFile.getParentFile().mkdirs();
        }
    }

}
