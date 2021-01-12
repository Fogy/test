package com.vtu.controller;

import com.vtu.model.Customer;
import com.vtu.model.Manage;
import com.vtu.service.impl.LoginServiceImpl;
import com.vtu.service.impl.ManageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.security.GeneralSecurityException;

@RestController
public class LoginController {

    @Autowired
    LoginServiceImpl loginService;

    @Autowired
    ManageServiceImpl manageService;



    @RequestMapping(value = "/login/email", method = RequestMethod.POST)
    public String loginByEmail(String email) throws MessagingException, GeneralSecurityException {
        return loginService.loginByEmail(email);
    }

    @RequestMapping(value = "/back/email/{authCode}", method = RequestMethod.GET)
    public Customer backFromEmail(@PathVariable("authCode") String authCode, HttpServletRequest request) throws MessagingException {
        return loginService.backFromEmail(authCode, request);
    }

    /**
     * 管理员用户查询
     * @param manage
     * @return
     */
    @RequestMapping(value = "/login/findManage", method = RequestMethod.POST)
    public Manage findManage(@RequestBody Manage manage){

        return manageService.findMange(manage);
    }
}
