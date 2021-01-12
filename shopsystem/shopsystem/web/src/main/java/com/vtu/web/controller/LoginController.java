package com.vtu.web.controller;

import com.vtu.model.BaseResult;
import com.vtu.model.Customer;
import com.vtu.web.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {
    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/login/email", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult loginByEmail(@RequestParam("email")String email, HttpServletRequest request) {
        String info = loginService.loginByEmail(email);
        BaseResult result = new BaseResult();
        if (!"fail".equals(info)) {
            result.setCode(200);
        }
        result.setValue(info);
        return result;
    }

    @RequestMapping(value = "/back/email/{authCode}", method = RequestMethod.GET)
    public void backFromEmail(@PathVariable("authCode")String authCode, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Customer customer = loginService.backFromEmail(authCode);
        request.getSession().setAttribute("customer",customer);
        if (customer != null){
            request.setAttribute("customer", customer);
        }
        response.sendRedirect("/");
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/logOut")
    public void logOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("customer");
        response.sendRedirect("/");
    }

}
