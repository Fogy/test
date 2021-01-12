package com.vtu.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.vtu.model.BaseResult;
import com.vtu.model.Works;
import com.vtu.web.service.PersonWorksManageService;
import org.springframework.stereotype.Component;

@Component
public class PersonWorksManageServiceImpl implements PersonWorksManageService {
    @Override
    public PageInfo<Works> getPersonManage(JSONObject jsonObject) {
        return null;
    }

    @Override
    public BaseResult delPersonMangeWorks(Works works) {
        return null;
    }
}
