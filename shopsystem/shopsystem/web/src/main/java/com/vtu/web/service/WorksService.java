package com.vtu.web.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.vtu.model.BaseResult;
import com.vtu.model.Works;
import com.vtu.web.service.impl.WorksServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 作品Service
 */
@FeignClient(value = "service",fallback = WorksServiceImpl.class)
public interface WorksService {

    @RequestMapping(value = "/insertWorks",method = RequestMethod.POST)
    BaseResult insertWorks(@RequestBody Works works);

    @RequestMapping(value = "/getWorksPath",method = RequestMethod.GET)
    String getWorksPath(JSONObject jsonObject);

    /**
     * 根据用户Id和作品状态查询
     * @param works
     * @return
     */
    @RequestMapping(value = "/getWorksByAccountId",method = RequestMethod.POST)
    List<Works> getWorksByAccountId(@RequestBody Works works);

    /**
     * 根据作品Id删除作品
     * @param worksIds
     * @return
     */
    @RequestMapping(value = "/delWorksById",method = RequestMethod.POST)
    BaseResult delWorksById(String worksIds);

    /**
     * 根据作品Id查询作品
     * @param worksIds
     * @return
     */
    @RequestMapping(value = "/getWorksById",method = RequestMethod.POST)
    Works getWorksById(String worksIds);

    /**
     * 更新作品
     * @param works
     * @return
     */
    @RequestMapping(value = "/updateWorks",method = RequestMethod.POST)
    BaseResult updateWorks(@RequestBody Works works);

    /**
     * 获取作品
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/getWorksByCondition",method = RequestMethod.POST)
    PageInfo getWorksByCondition(@RequestBody  JSONObject jsonObject);

    /**
     * 获取作品
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/searchWorks",method = RequestMethod.POST)
    PageInfo searchWorks(@RequestBody JSONObject jsonObject);

    /**
     * 购买作品
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/alipay/shopping",method = RequestMethod.POST)
    String aliPayShoping(@RequestBody JSONObject jsonObject);
}
