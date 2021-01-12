package com.vtu.mange.service;

import com.vtu.mange.service.impl.LoginServiceImpl;
import com.vtu.model.Manage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "service",fallback = LoginServiceImpl.class)
public interface LoginService {

    /**
     * 查询用户
     * @param manage
     * @return
     */
    @RequestMapping(value = "login/findManage",method = RequestMethod.GET)
    Manage findManage(Manage manage);
}
