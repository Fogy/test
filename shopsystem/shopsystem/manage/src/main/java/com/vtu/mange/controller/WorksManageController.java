package com.vtu.mange.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.vtu.mange.service.WorksService;
import com.vtu.model.BaseResult;
import com.vtu.model.Works;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/worksManage")
public class WorksManageController {

    @Autowired
    WorksService worksService;

    /**
     * 跳转到首页
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(){
        return "worksMange/index";
    }

    /**
     * 视频列表
     * @return
     */
    @RequestMapping(value = "/getWorks",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map getWorks(Works works,int length,int start,int draw){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("works",works);
        jsonObject.put("start",start/length+1);
        jsonObject.put("length",length);
        PageInfo pageInfo = worksService.getAuditingWorks(jsonObject);
        Map<String,Object> map = new HashMap<>();
        map.put("data",pageInfo.getList());
        map.put("recordsFiltered",pageInfo.getTotal());
        map.put("recordsTotal",pageInfo.getSize());
        return map;
    }

    /**
     * 根据作品Id获取作品
     * @param worksId
     * @return
     */
    @RequestMapping(value = "/getWorksById")
    @ResponseBody
    public Works getWorksById(String worksId){
        Works work = worksService.getWorksById(worksId);
        return work;
    }

    /**
     * 审核作品
     * @param works
     * @return
     */
    @RequestMapping(value = "/reviewWorks")
    @ResponseBody
    public BaseResult reviewWorks(Works works){
        BaseResult baseResult = worksService.reviewWorks(works);
        return baseResult;
    }




}
