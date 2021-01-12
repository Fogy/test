package com.vtu.web.controller.userProfile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.vtu.model.Customer;
import com.vtu.web.service.WorksDownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class WorksDownloadController {

    @Autowired
    WorksDownloadService worksDownloadService;


    /**
     * 获取用户购买记录
     * @param request
     * currentPage : data.page, //当前页数
     *                 totalPages : data.recordsTotal, //总页数
     *                 numberOfPages : data.pageSize,//每页记录数
     * @return
     */
    @RequestMapping(value = "/getWorksDownload",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map getWorksDownloads(HttpServletRequest request, int page,int pageSize){
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("page",page);
        jsonObject.put("length",pageSize);
        jsonObject.put("accountId",customer.getAccountId());
        PageInfo pageInfo = worksDownloadService.getWorksDownloads(jsonObject );
        Map<String,Object> map = new HashMap<>();
        map.put("data",pageInfo.getList());
        map.put("recordsFiltered",pageInfo.getTotal());
        map.put("page",pageInfo.getPrePage());
        map.put("totalPages",pageInfo.getPages());
        map.put("pageSize",pageInfo.getPageSize());
        map.put("recordsTotal",pageInfo.getSize());
        return  map;
    }
}
