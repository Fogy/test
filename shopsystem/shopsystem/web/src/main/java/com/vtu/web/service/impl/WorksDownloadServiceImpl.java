package com.vtu.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.vtu.model.Works;
import com.vtu.model.WorksDownload;
import com.vtu.web.service.WorksDownloadService;
import org.springframework.stereotype.Component;

@Component
public class WorksDownloadServiceImpl implements WorksDownloadService {
    @Override
    public Works getWorksDownload(Long accountId, Long worksId) {
        return null;
    }

    @Override
    public PageInfo<WorksDownload> getWorksDownloads(JSONObject jsonObject) {
        return null;
    }

}
