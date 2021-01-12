package com.vtu.mapper;

import com.vtu.model.Works;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WorksMapper {
    int deleteByPrimaryKey(Long worksId);

    Long insert(Works record);

    int insertSelective(Works record);

    Works selectByPrimaryKey(Long worksId);

    int updateByPrimaryKeySelective(Works record);

    int updateByPrimaryKey(Works record);

    /**
     * 查询作品
     * @param works
     * @return
     */
    List<Works> getWorksByCondition(@Param("works") Works works);

    /**
     * 获取个人作品列表
     * @param works
     * @return
     */
    List<Works> getPersonManage(@Param("works") Works works);
    /**
     * 根据accountId查询作品
     * @param works 用户Id
     * @return
     */
    List<Works> selectByAccountId(Works works);

    /**
     * 根据作品Id删除
     * @param worksIds
     * @return
     */
    Integer delWorksById(String worksIds);

    /**
     * 根据作品Id查询作品
     * @param worksIds
     * @return
     */
    Works getWorksById(String worksIds);

    /**
     * 更新作品
     * @param works
     */
    /*int updateWorks(@Param("works") Works works);*/


    /**
     * 根据作品Id查询作品
     * @param worksId
     * @return
     */
    Works getWorksDetialsById(Long worksId);

    /**
     * 审核作品
     * @param works works
     * @return
     */
    Integer auditingWorks(Works works);

    /**
     * 根据关键字查询作品列表
     * @param keyWord
     * @return
     */
    List<Works>searchWorksBykeyWord(String keyWord);

    /**
     * 根据关键字查询作品列表
     * @param works
     * @return
     */
    List<Works>searchWork(@Param("works") Works works);

}