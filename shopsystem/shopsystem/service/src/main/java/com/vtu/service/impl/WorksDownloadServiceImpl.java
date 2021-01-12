package com.vtu.service.impl;

import com.vtu.mapper.WorksDownloadMapper;
import com.vtu.model.WorksDownload;
import com.vtu.service.WorksDownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorksDownloadServiceImpl implements WorksDownloadService {
    @Autowired
    WorksDownloadMapper worksDownloadMapper;

    @Override
    public List<WorksDownload> findAllByAccountId(Long accountId) {
        return worksDownloadMapper.findAllByAccountId(accountId);
    }

    @Override
    public WorksDownload findByAccountIdAndWorksId(Long accountId, Long worksId) {
        return worksDownloadMapper.selectByAccountIdAndWorksId(accountId, worksId);
    }
}
