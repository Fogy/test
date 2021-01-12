package com.vtu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vtu.model.BaseResult;
import com.vtu.model.Works;
import com.vtu.service.impl.WorksServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 作品
 */
@RestController
public class WorksController {

    @Autowired
    WorksServiceImpl worksService;

    /**
     * 审核作品
     * @param works
     * @return
     */
    @RequestMapping(value = "/reviewWorks")
    public BaseResult reviewWorks(@RequestBody Works works){
        Integer num = worksService.auditingWorks(works);
        BaseResult baseResult = new BaseResult();
        if(num >=0){
        baseResult.setCode(200);
        }
        return baseResult;
    }


    /**
     * 更新作品
     * @param works
     * @return
     */
    @RequestMapping(value = "/updateWorks")
    public BaseResult updateWorks(@RequestBody  Works works){
        BaseResult baseResult = worksService.updateWorks(works);
        return baseResult;
    }

    /**
     * 插入作品
     *
     * @param works 作品
     * @return 插入结果
     */
    @RequestMapping(value = "/insertWorks")
    public BaseResult insertWorks(@RequestBody Works works) {
        BaseResult baseResult = worksService.insert(works);
        return baseResult;
    }


    @RequestMapping(value = "/getWorksPath")
    public String getWorksPath(@RequestBody JSONObject jsonObject){
        return worksService.getWorksPath(jsonObject);
    }


    /**
     * 根据用户Id获取作品
     * @param works
     * @return
     */
    @RequestMapping(value = "/getWorksByAccountId")
    public List<Works> selectByAccountId(@RequestBody Works works){
        return worksService.selectByAccountId(works);
    }

    /**
     * 根据作品Id删除
     * @param worksIds
     * @return
     */
    @RequestMapping(value = "/delWorksById")
    public BaseResult delWorksById(@RequestBody String worksIds){
        return worksService.delWorksById(worksIds);
    };

    /**
     * 根据作品Id查询作品
     * @param worksIds
     * @return
     */
    @RequestMapping(value = "/getWorksById")
    public Works getWorksById(@RequestBody String worksIds){
        return worksService.getWorksById(worksIds);
    }


    /**
     * 分页获取所有待审核的作品
     * @return
     */
    @RequestMapping(value = "/getWorksByCondition")
    public PageInfo getAuditingWorks(@RequestBody JSONObject jsonObject){
        PageHelper.startPage(jsonObject.getInteger("start"), jsonObject.getInteger("length"));
        List<Works> worksList = worksService.getWorksByCondition(jsonObject.getObject("works",Works.class));
        PageInfo pageInfo = new PageInfo(worksList);
        return pageInfo;
    }

    /**
     * 用户作评管理获取
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/getPersonManage")
    public PageInfo getPersonManage(@RequestBody JSONObject jsonObject){
        PageHelper.startPage(jsonObject.getInteger("page"), jsonObject.getInteger("pageSize"));
        List<Works> worksList = worksService.getPersonManage(jsonObject.getObject("works",Works.class));
        PageInfo pageInfo = new PageInfo(worksList);
        return pageInfo;
    }

    /**
     * 删除y用户管理作品
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/delPersonMangeWorks")
    public BaseResult delPersonMangeWorks(@RequestBody JSONObject jsonObject){
        Works works = jsonObject.toJavaObject(Works.class);
        BaseResult baseResult = worksService.updateWorks(works);
        return baseResult;
    }

    /**
     * 根据worksId获取作品的详细信息
     * @param worksId
     * @return
     */
    @RequestMapping(value = "/get/auditing/works/details")
    public Works getWorksDetails(@RequestParam("worksId") Long worksId){
        return worksService.getWorksDetialsById(worksId);
    }

    /**
     * 审核作品
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/auditing/works")
    public Integer auditingWorks(@RequestBody JSONObject jsonObject){
        Works works =JSON.toJavaObject(jsonObject,Works.class);
        return worksService.auditingWorks(works);
    }

    /**
     * 根据关键字分页查询作品列表
     * @param keyWord
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/search/works")
    public PageInfo searchWorksBykeyWord(@RequestParam("key") String keyWord,
                                         @RequestParam("pageNum") Integer pageNum,
                                         @RequestParam("pageSize") Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Works> worksList = worksService.searchWorksBykeyWord(keyWord);
        PageInfo pageInfo = new PageInfo(worksList);
        return pageInfo;
    }

    /**
     * 分页获取所有作品
     * @return
     */
    @RequestMapping(value = "/searchWorks")
    public PageInfo searchWorks(@RequestBody JSONObject jsonObject){
        PageHelper.startPage(jsonObject.getInteger("start"), jsonObject.getInteger("length"));
        List<Works> worksList = worksService.searchWorks(jsonObject.getObject("works",Works.class));
        PageInfo pageInfo = new PageInfo(worksList);
        return pageInfo;
    }



}
