package com.vtu.mapper;

import com.vtu.model.WorksDownload;

import java.util.List;

public interface WorksDownloadMapper {
    int deleteByPrimaryKey(Long downId);

    int insert(WorksDownload record);

    int insertSelective(WorksDownload record);

    WorksDownload selectByPrimaryKey(Long downId);

    int updateByPrimaryKeySelective(WorksDownload record);

    int updateByPrimaryKey(WorksDownload record);

    WorksDownload selectByAccountIdAndWorksId(Long accountId, Long worksId);

    /**
     *根据用户用查询用户下载记录
     * @param accountId
     * @return
     */
    List<WorksDownload> findAllByAccountId(Long accountId);


}