package com.vtu.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.vtu.model.BaseResult;
import com.vtu.model.Fav;
import com.vtu.web.service.FavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FavController {

    @Autowired
    FavService favService;

    @RequestMapping(value = "/addFav", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult addFav(@RequestBody JSONObject jsonObject){
        BaseResult result = favService.addFav(jsonObject);
        return result;
    }

    @RequestMapping(value = "/getFav", method = RequestMethod.GET)
    @ResponseBody
    public List<Fav> getFav(Long accountId){
        List<Fav> favList = favService.getFav(accountId);
        return favList;
    }


    @RequestMapping(value = "/delFav", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult delFav(Long favId){
        BaseResult baseResult = favService.delFav(favId);
        return baseResult;
    }

}
