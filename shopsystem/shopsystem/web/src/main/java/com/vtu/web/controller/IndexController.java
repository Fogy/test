package com.vtu.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.vtu.model.BaseResult;
import com.vtu.model.Customer;
import com.vtu.model.Works;
import com.vtu.web.config.AliyunOSSProperties;
import com.vtu.web.service.LoginService;
import com.vtu.web.service.WorksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 首页访问
 */
@Controller
public class IndexController {
    @Autowired
    private WorksService worksService;
    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/")
    public String goIndex(HttpServletRequest request) {
        Customer customer = loginService.backFromEmail("1234");
        // 给customer加入阿里云地址，用于获取oss服务器图片/视频
        customer.setDomainApp(AliyunOSSProperties.OSS_DOM_APP);
        request.getSession().setAttribute("customer", customer);
        return "index/index";
    }


    /**
     * 主页视频作品获取
     *
     * @param works
     * @param pageSize
     * @param page
     * @return
     */
    @RequestMapping(value = "/getWorksSearch")
    @ResponseBody
    public Map getWorksSearch(Works works, int pageSize, int page) {
        JSONObject jsonObject = new JSONObject();
        works.setStatus("pass");
        jsonObject.put("works", works);
        jsonObject.put("start", page);
        jsonObject.put("length", pageSize);
/*        jsonObject.put("type",works.getWorksType());
        jsonObject.put("version",works.getVersion());
        jsonObject.put("resolution",works.getResolution());
        jsonObject.put("channel",works.getResolution());
        jsonObject.put("worksStatus","");*/

        PageInfo pageInfo = worksService.getWorksByCondition(jsonObject);
        Map<String, Object> map = new HashMap<>();
        if (null != pageInfo && null != pageInfo.getList()) {
            map.put("list", pageInfo.getList());
        }
        map.put("recordsFiltered", pageInfo.getTotal());
        map.put("recordsTotal", pageInfo.getSize());
        map.put("pageSize", pageSize);
        map.put("page", page);
        return map;
    }

    /**
     * 主页视频作品获取
     *
     * @param works
     * @param length
     * @param start
     * @return
     */
    @RequestMapping(value = "/getWorksByCondition")
    @ResponseBody
    public Map getWorks(Works works, int length, int start) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("works", works);
        jsonObject.put("start", start / length + 1);
        jsonObject.put("length", length);
        PageInfo pageInfo = worksService.getWorksByCondition(jsonObject);
        Map<String, Object> map = new HashMap<>();
        map.put("list", pageInfo.getList());
        map.put("recordsFiltered", pageInfo.getTotal());
        map.put("recordsTotal", pageInfo.getSize());
        return map;
    }

    /**
     * 主页视频作品获取
     *
     * @param works
     * @param length
     * @param start
     * @return
     */
    @RequestMapping(value = "/index/searchWorks")
    @ResponseBody
    public PageInfo searchWorks(Works works, int length, int start) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("works", works);
        jsonObject.put("start", start / length + 1);
        jsonObject.put("length", length);
        PageInfo pageInfo = worksService.searchWorks(jsonObject);
        return pageInfo;
    }




}
