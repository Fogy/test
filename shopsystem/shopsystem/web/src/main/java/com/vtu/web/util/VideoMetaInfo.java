package com.vtu.web.util;

import lombok.Data;

/**
 * Author: dreamer-1
 * Date: 2018/5/7
 * Time: 16:32
 * Description: 视频数据基本信息类
 */
@Data
public class VideoMetaInfo extends MetaInfo {
    /**
     * 视频时长 ,单位：毫秒
     */
    private Long duration;

    /**
     * 原始视频长度
     */
    private String durinfo;
    /**
     * 比特率，单位：Kb/s
     * 指视频每秒传送的比特数
     */
    private Integer bitRate;
    /**
     * 编码器
     */
    private String decoder;
    /**
     * 帧率，单位：FPS（Frame Per Second）
     * 指视频每秒包含的帧数
     */
    private Float frameRate;

    private String encoder;

    private MusicMetaInfo musicMetaInfo;

    public MusicMetaInfo getMusicMetaInfo() {
        return musicMetaInfo;
    }

    public void setMusicMetaInfo(MusicMetaInfo musicMetaInfo) {
        this.musicMetaInfo = musicMetaInfo;
    }

    public String getEncoder() {
        return encoder;
    }

    public void setEncoder(String encoder) {
        this.encoder = encoder;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Integer getBitRate() {
        return bitRate;
    }

    public void setBitRate(Integer bitRate) {
        this.bitRate = bitRate;
    }

    public String getDecoder() {
        return decoder;
    }

    public void setDecoder(String decoder) {
        this.decoder = decoder;
    }

    public Float getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(Float frameRate) {
        this.frameRate = frameRate;
    }

    public String getDurinfo() {
        return durinfo;
    }

    public void setDurinfo(String durinfo) {
        this.durinfo = durinfo;
    }
}
