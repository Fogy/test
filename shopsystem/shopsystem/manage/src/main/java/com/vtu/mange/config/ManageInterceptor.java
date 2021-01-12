package com.vtu.mange.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManageInterceptor implements HandlerInterceptor{

    private final  Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Object manage = request.getSession().getAttribute("manage");

        if(null != manage) {
            return true;
        }else {
            logger.info("未登录URL:{}已被拦截",request.getRequestURI());
            response.sendRedirect("/login");
            return false;
        }
    }

   /* @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        //response.sendRedirect("/login");
        System.out.println("================= jin ru  after ===============");
    }*/
}
