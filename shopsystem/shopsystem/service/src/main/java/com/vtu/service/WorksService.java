package com.vtu.service;

import com.alibaba.fastjson.JSONObject;
import com.vtu.model.BaseResult;
import com.vtu.model.Works;

import java.util.List;

public interface WorksService {

    /**
     * 插入作品
     * @param works 作品
     * @return
     */
    BaseResult insert(Works works);
    String getWorksPath(JSONObject jsonObject);

    /**
     * 根据accountId查询作品
     * @param works works
     * @return
     */
    List<Works> selectByAccountId(Works works);

    /**
     * 根据作品Id删除
     * @param worksIds
     * @return
     */
    BaseResult delWorksById(String worksIds);

    /**
     * 根据作品Id查询作品
     * @param worksIds
     * @return
     */
    Works getWorksById(String worksIds);

    /**
     * 更新作品
     * @param works
     * @return
     */
    BaseResult updateWorks(Works works);

    /**
     * 查询所有待审核的作品
     * @return
     * @param works
     */
    List<Works> getWorksByCondition(Works works);

    /**
     * 获取跟个人作品列表
     * @param works
     * @return
     */
    List<Works> getPersonManage(Works works);

    /**
     * 根据作品Id查询作品
     * @param worksId
     * @return
     */
    Works getWorksDetialsById(Long worksId);

    /**
     * 审核作品
     * @param works
     * @return
     */
    Integer auditingWorks(Works works);

    /**
     * 根据关键字获取作品列表
     * @param keyWord
     * @return
     */
    List<Works>searchWorksBykeyWord(String keyWord);

    /**
     * 根据查询条件
     * @param works
     * @return
     */
    List<Works> searchWorks(Works works);
}
