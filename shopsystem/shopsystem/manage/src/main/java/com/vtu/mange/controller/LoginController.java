package com.vtu.mange.controller;


import com.vtu.mange.service.LoginService;
import com.vtu.model.Manage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/login")
    public String login(){
        return "login/login";
    }

    @RequestMapping(value = "/subLogin")
    @ResponseBody
    public String subLogin(HttpServletRequest request, HttpServletResponse response,Manage manage) throws IOException {

        String errorMsg= "用户名或密码错误";
        Manage searManage = loginService.findManage(manage);

        if(null == searManage){
            return errorMsg;
        }
        //第一个为明文，第二个为密文
        if(bCryptPasswordEncoder.matches(manage.getManagePassword(),searManage.getManagePassword())){
            HttpSession session = request.getSession();
            session.setAttribute("manage",searManage);
            //response.sendRedirect("/");
            return "success";

        }
        return errorMsg;

    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @RequestMapping(value = "/logOut")
    public String logOut(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("manage");
        return "login/login";
    }

}
