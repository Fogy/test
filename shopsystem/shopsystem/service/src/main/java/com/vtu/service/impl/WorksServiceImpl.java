package com.vtu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.vtu.mapper.PreviewUrlMapper;
import com.vtu.mapper.WorksDownloadMapper;
import com.vtu.mapper.WorksMapper;
import com.vtu.model.BaseResult;
import com.vtu.model.Works;
import com.vtu.model.WorksDownload;
import com.vtu.service.WorksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorksServiceImpl implements WorksService {

    @Autowired
    WorksMapper worksMapper;


    @Autowired
    PreviewUrlMapper previewUrlMapper;

    @Autowired
    WorksDownloadMapper worksDownloadMapper;


    /**
     * 插入作品
     *
     * @param works 作品
     * @return
     */
    @Override
    public BaseResult insert(Works works) {
        BaseResult baseResult = new BaseResult();
        Long worksId = worksMapper.insert(works);
        if (worksId <= 0) {
            baseResult.setCode(500);
        }
        return baseResult;
    }

    @Override
    public String getWorksPath(JSONObject jsonObject) {
        Long accountId = jsonObject.getLong("accountId");
        Long worksId = jsonObject.getLong("worksId");
        WorksDownload worksDownload =  worksDownloadMapper.selectByAccountIdAndWorksId(accountId, worksId);
        if(worksDownload != null){
           Works worksUrl = worksMapper.selectByPrimaryKey(worksId);
           return worksUrl.getWorksUrl();
        }else {
            return null;
        }

    }

    /**
     * 根据accountId查询作品
     * @param works 用户Id
     * @return
     */
    @Override
    public List<Works> selectByAccountId(Works works) {
        List<Works> worksList = worksMapper.selectByAccountId(works);
        return worksList;
    }

    /**
     * 根据作品Id删除作品
     * @param worksIds
     * @return
     */
    @Override
    public BaseResult delWorksById(String worksIds) {
        BaseResult baseResult = new BaseResult();
        Integer num = worksMapper.delWorksById(worksIds);
        if(num>0){
            baseResult.setCode(200);
        }
        return baseResult;
    }

    /**
     * 根据作品Id查询作品
     * @param worksIds
     * @return
     */
    @Override
    public Works getWorksById(String worksIds) {
        return worksMapper.getWorksById(worksIds);
    }

    /**
     * 更新作品
     * @param works
     * @return
     */
    @Override
    public BaseResult updateWorks(Works works) {
        BaseResult baseResult = new  BaseResult();
        int upWorksNum = worksMapper.updateByPrimaryKeySelective(works);
        if(upWorksNum>0 ){
            baseResult.setCode(200);
        }
        return baseResult;
    }

    /**
     * 获取所有待审核的作品
     * @return
     * @param works
     */
    @Override
    public List<Works> getWorksByCondition(Works works) {
        List<Works> worksList= worksMapper.getWorksByCondition(works);
        return worksList;
    }

    /**
     * 获取个人作品列表
     * @param works
     * @return
     */
    @Override
    public List<Works> getPersonManage(Works works) {
        List<Works> worksList= worksMapper.getPersonManage(works);
        return worksList;
    }


    /**
     * 根据作品Id查询作品
     * @param worksId
     * @return
     */
    @Override
    public Works getWorksDetialsById(Long worksId) {
        return worksMapper.getWorksDetialsById(worksId);
    }

    /**
     * 根据作品Id查询作品
     * @param works
     * @return
     */
    @Override
    public Integer auditingWorks(Works works) {
        return worksMapper.auditingWorks(works);
    }

    /**
     * 根据关键字获取作品列表
     * @param keyWord
     * @return
     */
    @Override
    public List<Works> searchWorksBykeyWord(String keyWord) {
        return worksMapper.searchWorksBykeyWord(keyWord);
    }


    /**
     * 获取所有待审核的作品
     * @return
     * @param works
     */
    @Override
    public List<Works> searchWorks(Works works) {
        List<Works> worksList= worksMapper.searchWork(works);
        return worksList;
    }



}
