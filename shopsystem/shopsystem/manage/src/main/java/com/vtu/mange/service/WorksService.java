package com.vtu.mange.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.vtu.mange.service.impl.WorksServiceImpl;
import com.vtu.model.BaseResult;
import com.vtu.model.Works;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service",fallback = WorksServiceImpl.class)
public interface WorksService {

    /**
     * 获取作品
     *
     */
    @RequestMapping(value = "/getWorksByCondition",method = RequestMethod.POST)
    PageInfo getAuditingWorks(JSONObject jsonObject);

    /**
     * 根据作品Id获取作品
     * @param worksIds
     * @return
     */
    @RequestMapping(value = "/getWorksById",method = RequestMethod.POST)
    Works getWorksById(String worksIds);

    /**
     * 审核作品
     * @param works
     * @return
     */
    @RequestMapping(value = "/reviewWorks",method = RequestMethod.POST)
    BaseResult reviewWorks(Works works);
}
