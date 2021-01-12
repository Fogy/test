package com.vtu.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vtu.model.Works;
import com.vtu.model.WorksDownload;
import com.vtu.service.impl.WorksDownloadServiceImpl;
import com.vtu.service.impl.WorksServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WorksDownloadController {

    @Autowired
    WorksDownloadServiceImpl worksDownloadService;

    @Autowired
    WorksServiceImpl worksService;

    @RequestMapping(value = "/getWorksDownloads", method = RequestMethod.POST)
    public PageInfo<WorksDownload> getWorksDownloads(@RequestBody JSONObject jsonObject){
        PageHelper.startPage(jsonObject.getInteger("page"), jsonObject.getInteger("length"));
        List<WorksDownload> worksDownloadList = worksDownloadService.findAllByAccountId(jsonObject.getLong("accountId"));
        PageInfo pageInfo = new PageInfo(worksDownloadList);
        return pageInfo;
    }

    /**
     * 获取下载作品详细信息
     * 如果没有则返回null
     * @param accountId
     * @param worksId
     * @return
     */
    @RequestMapping(value = "/getWorksDownload", method = RequestMethod.GET)
    public Works getWorksDownload(@RequestParam("accountId") Long accountId,
                                        @RequestParam("worksId") Long worksId){
        WorksDownload worksDownload = worksDownloadService.findByAccountIdAndWorksId(accountId, worksId);
        if (worksDownload != null){
            Works works = worksService.getWorksDetialsById(worksId);
            return works;
        }else {
            return null;
        }
    }


}
