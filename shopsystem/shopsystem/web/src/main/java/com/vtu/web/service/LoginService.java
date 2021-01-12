package com.vtu.web.service;

import com.vtu.model.Customer;
import com.vtu.model.Works;
import com.vtu.web.service.impl.LoginServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "service", fallback = LoginServiceImpl.class)
public interface LoginService {
    @RequestMapping(value = "/login/email", method = RequestMethod.POST)
    String loginByEmail(@RequestParam("email")String email);

    @RequestMapping(value = "/back/email/{authCode}", method = RequestMethod.GET)
    Customer backFromEmail(@PathVariable("authCode")String authCode);



}
