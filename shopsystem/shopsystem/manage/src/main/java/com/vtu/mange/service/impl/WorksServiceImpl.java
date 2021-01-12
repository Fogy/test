package com.vtu.mange.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.vtu.mange.service.WorksService;
import com.vtu.model.BaseResult;
import com.vtu.model.Works;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class WorksServiceImpl implements WorksService {

    /**
     * 获取作品
     *
     * @return
     */
    @Override
    public PageInfo getAuditingWorks(@RequestParam("jsonObject") JSONObject jsonObject) {
        return null;
    }

    /**
     * 根据作品Id获取作品
     * @param worksId
     * @return
     */
    @Override
    public Works getWorksById(String worksId) {
        return null;
    }

    @Override
    public BaseResult reviewWorks(Works works) {
        return null;
    }
}
