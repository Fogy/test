package com.vtu.web.controller.userProfile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.vtu.model.BaseResult;
import com.vtu.model.Customer;
import com.vtu.model.Works;
import com.vtu.web.service.PersonWorksManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PersonWorksManageController {
    @Autowired
    PersonWorksManageService pService;

    /**
     * 删除个人作品
     * @param request
     * @param works
     * @return
     */
    @RequestMapping(value = "/delPersonMangeWorks",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public BaseResult delPersonMangeWorks(HttpServletRequest request,Works works){
        BaseResult baseResult = new BaseResult();
        baseResult = pService.delPersonMangeWorks(works);
        return baseResult;
    }
    /**
     * 获取个人作品管理列表
     * @param request
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/getPersonManage",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map getPersonManage(HttpServletRequest request, int page, int pageSize){
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        Works works = new Works();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("page",page);
        jsonObject.put("pageSize",pageSize);
        works.setAccountId(customer.getAccountId());
        jsonObject.put("works",works);
        PageInfo pageInfo = pService.getPersonManage(jsonObject );
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
