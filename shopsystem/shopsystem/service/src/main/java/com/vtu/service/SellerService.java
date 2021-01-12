package com.vtu.service;

import com.alibaba.fastjson.JSONObject;
import com.vtu.model.BaseResult;
import org.springframework.web.bind.annotation.RequestBody;

public interface SellerService {
    BaseResult cashOutApply(@RequestBody JSONObject jsonObject);
}
