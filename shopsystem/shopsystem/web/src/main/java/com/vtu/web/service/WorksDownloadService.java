package com.vtu.web.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.vtu.model.Works;
import com.vtu.model.WorksDownload;
import com.vtu.web.service.impl.WorksDownloadServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service",fallback = WorksDownloadServiceImpl.class)
public interface WorksDownloadService {
    @RequestMapping(value = "/getWorksDownload",method = RequestMethod.GET)
    Works getWorksDownload(@RequestParam("accountId") Long accountId,@RequestParam("worksId") Long worksId);

    @RequestMapping(value = "/getWorksDownloads",method = RequestMethod.POST)
    PageInfo<WorksDownload> getWorksDownloads(@RequestBody JSONObject jsonObject);
}
