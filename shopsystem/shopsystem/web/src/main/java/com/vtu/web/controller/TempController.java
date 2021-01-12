package com.vtu.web.controller;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.vtu.model.*;
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
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
public class TempController {

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
    @RequestMapping(value = "/uploadTemp",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult uploadTemp(HttpServletRequest request){
        MultipartHttpServletRequest mrequest;
        Customer customer = (Customer) request.getSession().getAttribute("customer");

        BaseResult baseResult = new BaseResult();
        if(request instanceof MultipartHttpServletRequest){
            mrequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> listSynthesis = mrequest.getFiles("synthesis");
            List<MultipartFile> listCompress = mrequest.getFiles("compress");
            baseResult = getBaseResult(mrequest,customer);
        } else {
            baseResult.setCode(500);
            baseResult.setMsg("请选择文件");
        }
        return baseResult;
    }

    //上传你保存作品
    private BaseResult getBaseResult(MultipartHttpServletRequest mrequest, Customer customer) {
        List<MultipartFile> listSynthesis = mrequest.getFiles("synthesis");
        List<MultipartFile> listCompress = mrequest.getFiles("compress");

        MultipartFile compress = listSynthesis.get(0);
        MultipartFile synthesis = listCompress.get(0);

        OSS ossClient = getOssClient();
        /*Temps temps = new Temps();*/
        Works works = new Works();
        long times = System.currentTimeMillis();
        //文件名称
        String originalName= compress.getOriginalFilename();
        //预览文件名称
        String videoName= compress.getOriginalFilename();
        //String compressName= compress.getOriginalFilename();
        //uuid文件名称
        String uuIdFileNme = UUID.randomUUID().toString().replace("-", "")+videoName.substring(videoName.indexOf("."),videoName.length());
        //输入输出总路径
        String inPath = videoImport+ File.separator+times;
        String outPath = videoOutput+File.separator+times;

        //
        String videoPath=inPath+File.separator+uuIdFileNme;
        String zipPath = inPath+File.separator+synthesis.getOriginalFilename();
        //原视频文件
        File videoFile = new File(videoPath);
        File zipFile = new File(zipPath);
        //预览图片文件夹
        File prevFile = new File(outPath+File.separator+"preview");
        //合成图片的路径
        String fileName = videoName.substring(0,videoName.indexOf("."));
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
            FileUtils.writeByteArrayToFile(videoFile,compress.getBytes());
            FileUtils.writeByteArrayToFile(zipFile,synthesis.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取视频信息
        VideoMetaInfo videoMetaInfo = MediaUtil.getVideoMetaInfo(videoFile);
        processVideo(videoFile, prevFile, synthesisFile,compressFile, logFile,videoMetaInfo);
        //获取ossClient
        //上传原始视频
        String videoUrl="temps"+"/"+"tempImport"+"/"+ times+"/"+videoFile.getName();
        String zipUrl="temps"+"/"+"tempImport"+"/"+ times+"/"+zipFile.getName();

        //上传压缩视频
        String compressVideoUrl = "temps"+"/"+"tempOutput"+"/"+ times+"/"+"compress"+"/"+originalName;
        works.setCompressUrl(AliyunOSSProperties.OSS_DOM_APP+compressVideoUrl);

        //上传合成视频
        String synthesisVideoUrl = "temps"+"/"+"tempOutput"+"/"+ times+"/"+"synthesis"+"/"+synthesisFile.getName();
        works.setSynthesisUrl(AliyunOSSProperties.OSS_DOM_APP+synthesisVideoUrl);
        //上传预览图片
        File[] prevFiles=prevFile.listFiles();
        File prev = prevFiles[prevFiles.length-1];
        String prevUrl = "temps"+"/"+"tempOutput"+"/"+ times+"/"+"preview"+"/"+prev.getName();
        works.setPreviewUrl(AliyunOSSProperties.OSS_DOM_APP+prevUrl);
        try {
            works.setMd5(DigestUtils.md5DigestAsHex(synthesis.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        works.setSize((double)compress.getSize());
        //worksUrl.setWorksName(video.getOriginalFilename());
        upLoadOSS(videoUrl, videoFile, ossClient);
        upLoadOSS(zipUrl, zipFile, ossClient);
        upLoadOSS(compressVideoUrl, compressFile, ossClient);
        upLoadOSS(synthesisVideoUrl, synthesisFile, ossClient);
        upLoadOSS(prevUrl, prev, ossClient);
        ossClient.shutdown();
        //存贮视频
        works.setWorksUrl(aliyunOSSProperties.getDomainApp()+videoUrl);
        works.setZipUrl(aliyunOSSProperties.getDomainApp()+zipUrl);
        works.setTitle(synthesis.getOriginalFilename().substring(0,synthesis.getOriginalFilename().lastIndexOf(".")));
        works.setVersion(videoName.substring(videoName.lastIndexOf(".")+1));
        works.setName(synthesis.getOriginalFilename());
        works.setStatus("refer");
        works.setType("2");
        //此处要修改
        works.setAccountId(customer.getAccountId());
        //视频时长
        double duration = Math.floor(videoMetaInfo.getDuration()/1000);

        works.setDuration(videoMetaInfo.getDurinfo());
        BaseResult baseResult = worksService.insertWorks(works);

        //baseResult = worksService.insertWorks(works);
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
     *  @param videoFile
     * @param prevFile
     * @param synthesisFile
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
        double frameNum =  duration*videoMetaInfo.getFrameRate();
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
     * 保存提交审核作品
     * @param
     * @return
     */
    @RequestMapping(value = "/saveUpTemplates")
    public BaseResult saveUpTemplates(HttpServletRequest request){
        BaseResult baseResult = null;
        MultipartHttpServletRequest mrequest;
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        String worksId = request.getParameter("worksId");
        Works works = worksService.getWorksById(worksId);
        //works.setType( request.getParameter("type"));
        works.setDuration( request.getParameter("duration"));
        works.setTitle( request.getParameter("title"));
        works.setKeyWord( request.getParameter("keyWord"));
        String price = request.getParameter("price");
        if(StringUtils.isNotEmpty(price)){
            works.setPrice(new BigDecimal(request.getParameter("price")));
        }
        works.setModifyScope(request.getParameter("modifyScope"));
        works.setColorsytem( request.getParameter("colorSytem"));
        works.setChannel( request.getParameter("channel"));
        works.setMaterial( request.getParameter("material"));
        works.setMaterialType( request.getParameter("materialType"));
        works.setResolution( request.getParameter("resolution"));
        works.setVersion(request.getParameter("materialType"));
        works.setStatus("auditing");
        works.setType("2");
        works.setShoperType(customer.getShoperType());

        String previewUrl = works.getPreviewUrl();
        String synthesisUrl = works.getSynthesisUrl();

        if(request instanceof MultipartHttpServletRequest){
            mrequest = (MultipartHttpServletRequest) request;
            MultipartFile previewUrlFile = mrequest.getFile("template_previewUrl");
            MultipartFile synthesisUrlFile = mrequest.getFile("template_synthesisUrl");
            OSS ossClient = getOssClient();
            try {
                if(StringUtils.isNotBlank(previewUrlFile.getOriginalFilename())){
                    previewUrl = getPreviewUrl(ossClient,previewUrlFile,previewUrl);
                }
                if(StringUtils.isNotBlank(synthesisUrlFile.getOriginalFilename())){
                    synthesisUrl = getPreviewUrl(ossClient,synthesisUrlFile,synthesisUrl);
                }
                works.setPreviewUrl(previewUrl);
                works.setSynthesisUrl(synthesisUrl);
            } catch (IOException e) {
                baseResult.setCode(300);
                baseResult.setMsg("上传文件失败");
            }
            baseResult = worksService.updateWorks(works);

        }
        System.out.println(JSON.toJSONString(works,true));

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
