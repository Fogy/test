package com.vtu.web.util;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.Time;

public class MediaTest {
    private static Logger logger = LoggerFactory.getLogger(MediaTest.class);

    @Test
    public void testSynthesisPicture() throws IOException {
        File inFile = new File("E:\\infile");
        File outFile = new File("E:\\out\\dd.jpg");
        MediaUtil.synthesisPicture(inFile,outFile);
    }

    @Test
    public void testIsExecutable(){
        boolean executable = MediaUtil.isExecutable();
        logger.debug("测试额结果是:{}",executable);
    }

    /**
     * 截取图片
     */
    @Test
    public void testCutVideoFrame(){
        File inFile = new File("F:\\testFile\\cc.mov");
        File outFile = new File("F:\\bb\\aa.png");
        MediaUtil.cutVideoFrame(inFile,outFile);
    }

    /**
     * 视频截取
     */
    @Test
    public void testCutVideo(){
        File inFile = new File("F:\\testFile\\cc.mov");
        File outFile = new File("F:\\bb\\cc.gif");
        Time time = new Time(0, 0, 10);
        MediaUtil.cutVideo(inFile, outFile, time, 10);
    }

    @Test
    public void testVideoMetaInfo(){
        File inFile = new File("F:\\bb\\惊悚骷髅.mp4");
        VideoMetaInfo videoMetaInfo = MediaUtil.getVideoMetaInfo(inFile);
        System.out.println(JSON.toJSONString(videoMetaInfo,true));
    }

    @Test
    public void testCompress(){
        File inFile = new File("E:\\infile\\7.mp4");
        File logFile = new File("E:\\log\\logo.png");
        File outFile = new File("E:\\out\\7.mp4");
        VideoMetaInfo videoMetaInfo = MediaUtil.getVideoMetaInfo(inFile);


        MediaUtil.getPreviewVido2(inFile,logFile,outFile,videoMetaInfo);
        //MediaUtil.compressVideo(inFile,logFile,outFile);
    }

    //生成预览视频并添加水印
    @Test
    public void testGetPreviewVido(){
        File inFile = new File("F:\\bb\\惊悚骷髅.mp4");
        File logFile = new File("F:\\vtuweb\\vidoLog\\logo.png");
        File outFile = new File("F:\\bb\\cc.mp4");
        MediaUtil.getPreviewVido(inFile,logFile,"500k","10",outFile);
    }

    //生成预览图片
    @Test
    public void testPrev(){
        String starPath = "F:\\bb\\惊悚骷髅.mp4";
        String outPth ="F:\\cc";
        MediaUtil.getPreview(starPath,outPth,240.00,240.00,4.00);
    }

    @Test
    public void getMetaInfoFromFFmpeg(){
        File file = new File("/home/zhgurou/Videos/dde-introduction.mp4");
        VideoMetaInfo info = MediaUtil.getVideoMetaInfo(file);
        System.out.println(info);
    }
}
