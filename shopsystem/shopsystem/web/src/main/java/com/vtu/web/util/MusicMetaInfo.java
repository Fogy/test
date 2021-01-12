package com.vtu.web.util;

public class MusicMetaInfo extends MetaInfo {
    Long duration = 0L; // 视频时长
    Integer videoBitrate = 0; // 视频码率
    String videoFormat ; // 视频格式
    Long videoSize ; // 视频大小
    Long sampleRate = 0L; // 音频采样率
    Integer BitRate;

    public Integer getBitRate() {
        return BitRate;
    }

    public void setBitRate(Integer bitRate) {
        BitRate = bitRate;
    }

    public Long getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(Long sampleRate) {
        this.sampleRate = sampleRate;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Integer getVideoBitrate() {
        return videoBitrate;
    }

    public void setVideoBitrate(Integer videoBitrate) {
        this.videoBitrate = videoBitrate;
    }

    public String getVideoFormat() {
        return videoFormat;
    }

    public void setVideoFormat(String videoFormat) {
        this.videoFormat = videoFormat;
    }

    public Long getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(Long videoSize) {
        this.videoSize = videoSize;
    }
}
