package com.vtu.web.service;


import com.alibaba.fastjson.JSONObject;
import com.vtu.model.BaseResult;
import com.vtu.model.Fav;
import com.vtu.web.service.impl.FavServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "service", fallback = FavServiceImpl.class)
public interface FavService {
    @RequestMapping(value = "/addFav", method = RequestMethod.POST)
    BaseResult addFav(@RequestBody JSONObject jsonObject);

    @RequestMapping(value = "/getFav", method = RequestMethod.GET)
    List<Fav> getFav(@RequestParam("accountId") Long accountId);

    @RequestMapping(value = "/delFav", method = RequestMethod.GET)
    BaseResult delFav(@RequestParam("favId") Long favId);
}
