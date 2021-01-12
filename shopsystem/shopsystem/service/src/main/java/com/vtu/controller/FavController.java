package com.vtu.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vtu.model.BaseResult;
import com.vtu.model.Fav;
import com.vtu.service.impl.FavServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FavController {

    @Autowired
    FavServiceImpl favService;

    @RequestMapping(value = "/addFav", method = RequestMethod.POST)
    public BaseResult addFav(@RequestBody JSONObject jsonObject){
        Long accountId = jsonObject.getLong("accountId");
        Long worksId = jsonObject.getLong("worksId");
        return favService.addFav(accountId, worksId);
    }


    @RequestMapping(value = "/getFav", method = RequestMethod.GET)
    public PageInfo getFav(@RequestParam("accountId") Long accountId,
                            @RequestParam("pageNum") Integer pageNum,
                            @RequestParam("pageSize") Integer pageSize){

        PageHelper.startPage(pageNum, pageSize);
        List<Fav> favList = favService.getFav(accountId);
        PageInfo pageInfo = new PageInfo(favList);
        return pageInfo;
    }

    @RequestMapping(value = "/delFav", method = RequestMethod.GET)
    public BaseResult delFav(@RequestParam("favId") Long favId){
        return favService.delFav(favId);
    }
}
