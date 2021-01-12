package com.vtu.web.service;

import com.vtu.model.BaseResult;
import com.vtu.model.Temps;
import com.vtu.web.service.impl.TempServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 模板Service
 */
@FeignClient(value = "Service",fallback = TempServiceImpl.class)
public interface TempService {
    @RequestMapping(value = "/temps/insertTemps",method = RequestMethod.POST)
    BaseResult insert(@RequestBody Temps temps);

    /**
     * 获取未审核用户模板
     * @param temps
     * @return
     */
    @RequestMapping(value = "/temps/getReferTemps",method = RequestMethod.POST)
    List<Temps> getReferTemps(@RequestBody Temps temps);

}


