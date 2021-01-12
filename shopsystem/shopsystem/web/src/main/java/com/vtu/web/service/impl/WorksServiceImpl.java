package com.vtu.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.vtu.model.BaseResult;
import com.vtu.model.Works;
import com.vtu.web.service.WorksService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorksServiceImpl implements WorksService {
    @Override
    public BaseResult insertWorks(Works works) {
        return null;
    }

    @Override
    public String getWorksPath(JSONObject jsonObject) {
        return null;
    }

    @Override
    public List<Works> getWorksByAccountId(Works works) {
        return null;
    }

    @Override
    public BaseResult delWorksById(String worksIds) {
        return null;
    }

    @Override
    public Works getWorksById(String worksIds) {
        return null;
    }

    @Override
    public BaseResult updateWorks(Works works) {
        return null;
    }

    @Override
    public PageInfo getWorksByCondition(JSONObject jsonObject) {
        return null;
    }

    @Override
    public PageInfo searchWorks(JSONObject jsonObject) {
        return null;
    }

    @Override
    public String aliPayShoping(JSONObject jsonObject) {
        return null;
    }
}
