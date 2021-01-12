package com.vtu.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import com.vtu.model.Customer;
import com.vtu.model.Works;
import com.vtu.web.config.AliyunOSSProperties;
import com.vtu.web.service.PersonalBalanceService;
import com.vtu.web.service.WorksDownloadService;
import com.vtu.web.service.WorksService;
import com.vtu.web.util.AliyunUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Controller
public class VideoDetailController {
    private static Logger logger = LoggerFactory.getLogger(VideoDetailController.class);
    @Autowired
    WorksService worksService;

    @Autowired
    WorksDownloadService worksDownloadService;

    @Autowired
    AliyunUtils aliyunUtils;

    @Autowired
    PersonalBalanceService personalBalanceService;

    @Value("${aliyun-oss.domainApp}")
    String domApp;

    @Value("${video.videoDown}")
    String downPath;

    @Value("${video.indexPath}")
    String indexFile;

    private static final byte[] BUF = new byte[1024];


    @RequestMapping(value = "/judgeWorks")
    @ResponseBody
    public String judgeWorks(HttpServletRequest request,String worksIds){
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountId",customer.getAccountId());
        jsonObject.put("worksId",customer.getAccountId());
        Works works = worksDownloadService.getWorksDownload(customer.getAccountId(), Long.parseLong(worksIds));
        if(null != works){
            return "success";
        }
        else {
            return "false";
        }

    }


    /**
     * 下载作品
     *
     * @param response
     * @param worksIds
     * @return
     */
    @RequestMapping(value = "/downWorks")
    public String downWorks(HttpServletRequest request,HttpServletResponse response, String worksIds) {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountId",customer.getAccountId());
        jsonObject.put("worksId",customer.getAccountId());
        Works judegeWorks = worksDownloadService.getWorksDownload(customer.getAccountId(), Long.parseLong(worksIds));
        if (null == judegeWorks){
            return "/error";
        }
        logger.info("开始下载作品");
        Works works = worksService.getWorksById(worksIds);
        if (null != works) {
            String worksUrl = works.getWorksUrl();
            worksUrl= worksUrl.replace(domApp,"");

            OutputStream out = null;

            FileInputStream input =null;

            long curTimeMillis = System.currentTimeMillis();

            File downPathFile= new File(downPath);
            if(!downPathFile.exists()){
                downPathFile.mkdirs();
            }


            String downAliPath = downPath+curTimeMillis+".zip";
            File downfile = new File(downAliPath);
            ZipOutputStream zipOutputStream = null;
            FileOutputStream fileOutputStream = null;
            File zipFile  = new File(downAliPath);
            try {
                OSS ossClient = aliyunUtils.getOssClient();
                OSSObject ossObject = ossClient.getObject(AliyunOSSProperties.OSS_BUCKET_NAME,worksUrl);
                downfile.createNewFile();
                fileOutputStream = new FileOutputStream(zipFile);
                zipOutputStream = new ZipOutputStream(fileOutputStream);
                //writeFile(zipOutputStream, ossObject);
                String url = works.getWorksUrl();
                String sysUrl = works.getSynthesisUrl();
                String previewUrl = works.getPreviewUrl();
                String zipUrl = works.getZipUrl();
                String compressUrl = works.getCompressUrl();

                if(null !=url){
                    url= url.replace(domApp,"");
                    zipOutputStream.putNextEntry(new ZipEntry(works.getTitle()+url.substring(url.lastIndexOf("."))));
                    ossObject = ossClient.getObject(AliyunOSSProperties.OSS_BUCKET_NAME,url);
                    writeFile(zipOutputStream, ossObject);
                }
                if(null !=sysUrl && works.getType() != "3"){
                    sysUrl= sysUrl.replace(domApp,"");
                    zipOutputStream.putNextEntry(new ZipEntry("预览图1"+sysUrl.substring(sysUrl.lastIndexOf("."))));
                    ossObject = ossClient.getObject(AliyunOSSProperties.OSS_BUCKET_NAME,sysUrl);
                    writeFile(zipOutputStream, ossObject);
                }
                if(null !=previewUrl && works.getType() == "3"){
                    previewUrl= previewUrl.replace(domApp,"");
                    zipOutputStream.putNextEntry(new ZipEntry("预览图2"+previewUrl.substring(previewUrl.lastIndexOf("."))));
                    ossObject = ossClient.getObject(AliyunOSSProperties.OSS_BUCKET_NAME,previewUrl);
                    writeFile(zipOutputStream, ossObject);
                }
                if (null != compressUrl && works.getType() == "2"){
                    compressUrl= compressUrl.replace(domApp,"");
                    zipOutputStream.putNextEntry(new ZipEntry("预览视频"+compressUrl.substring(compressUrl.lastIndexOf("/")+1)));
                    ossObject = ossClient.getObject(AliyunOSSProperties.OSS_BUCKET_NAME,compressUrl);
                    writeFile(zipOutputStream, ossObject);
                }
                if(null !=zipUrl){
                    zipUrl= zipUrl.replace(domApp,"");
                    zipOutputStream.putNextEntry(new ZipEntry(zipUrl.substring(zipUrl.lastIndexOf("/")+1)));
                    ossObject = ossClient.getObject(AliyunOSSProperties.OSS_BUCKET_NAME,zipUrl);
                    writeFile(zipOutputStream, ossObject);
                }

                File inFile = new File(indexFile);
                InputStream indexInputStram = new FileInputStream(inFile);
                zipOutputStream.putNextEntry(new ZipEntry("vtuweb.com.html"));
                writeInputStream(zipOutputStream,indexInputStram);
                if(null != indexInputStram){
                    indexInputStram.close();
                }
                zipOutputStream.closeEntry();

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    if(zipOutputStream != null) {
                        zipOutputStream.close();
                    }
                    if(fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            try {
                response.setHeader("Content-Disposition",
                        "attachment;fileName=" + URLEncoder.encode(works.getTitle()+".zip", "UTF-8"));
                response.setContentType("application/x-msdownload;");
                input = new FileInputStream(zipFile);
                out = response.getOutputStream();
                byte[] buff = new byte[1024];
                int index = 0;
                while ((index = input.read(buff)) != -1) {
                    out.write(buff, 0, index);
                }
                out.flush();
                out.close();
                input.close();
                logger.info("完成作品下载");
            } catch (IOException e) {
                logger.info("开始下载作品失败:{}", e);
            } finally {
                try {
                    if (null != out) {
                        out.close();
                    }
                    if (null != input) {
                        input.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
        return "false";
    }

    private void writeFile(ZipOutputStream zipOutputStream, OSSObject ossObject) throws IOException {
        InputStream inputStream = ossObject.getObjectContent();
        writeInputStream(zipOutputStream,inputStream);
    }

    private void writeInputStream(ZipOutputStream zipOutputStream,InputStream inputStream) throws IOException {
        int len;
        while ((len = inputStream.read(BUF))>0){
            zipOutputStream.write(BUF,0,len);
        }
        inputStream.close();
    }

    /**
     * 跳转视频详情页
     *
     * @param request
     * @param worksIds
     * @return
     */
    @RequestMapping(value = "/getWorksDetail")
    public String getWorksDetail(HttpServletRequest request, String worksIds) {
        String res = "/works/video-detail";
        Works work = worksService.getWorksById(worksIds);
        request.setAttribute("work", work);
        if("2".equals(work.getType())){
            res = "/works/temp-detail";
        }else if("3".equals(work.getType())){
            res ="/works/plane-detail";
        }
        return res;
    }

    /**
     * 购买作品
     *
     * @param request
     * @param worksIds
     * @return
     */
    @RequestMapping(value = "/buyWorks")
    @ResponseBody
    public String buyWorks(HttpServletRequest request, String worksIds) {
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountId", customer.getAccountId());
        jsonObject.put("worksIds", worksIds);
        jsonObject.put("balance", "true");
        String buyStatus = worksService.aliPayShoping(jsonObject);
        return buyStatus;
    }
}
