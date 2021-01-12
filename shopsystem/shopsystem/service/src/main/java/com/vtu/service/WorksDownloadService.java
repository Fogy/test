package com.vtu.service;

import com.vtu.model.WorksDownload;

import java.util.List;

public interface WorksDownloadService {
    List<WorksDownload> findAllByAccountId(Long accountId);
    WorksDownload findByAccountIdAndWorksId(Long accountId, Long worksId);
}
