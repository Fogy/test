package com.vtu.web.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.vtu.model.BaseResult;
import com.vtu.model.Works;
import com.vtu.web.service.impl.PersonWorksManageServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "service", fallback = PersonWorksManageServiceImpl.class)
public interface PersonWorksManageService {
    @RequestMapping(value = "/getPersonManage",method = RequestMethod.POST)
    PageInfo<Works> getPersonManage(@RequestBody JSONObject jsonObject);


    /**
     * 用户作品软删除
     * @param works
     * @return
     */
    @RequestMapping(value = "delPersonMangeWorks",method = RequestMethod.POST)
    BaseResult delPersonMangeWorks(Works works);
}
