package com.vtu.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.vtu.model.BaseResult;
import com.vtu.model.Fav;
import com.vtu.web.service.FavService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FavServiceImpl implements FavService {
    @Override
    public BaseResult addFav(JSONObject jsonObject) {
        return null;
    }

    @Override
    public List<Fav> getFav(Long accountId) {
        return null;
    }

    @Override
    public BaseResult delFav(Long favId) {
        return null;
    }
}
