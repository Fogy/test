package com.vtu.controller;

import com.vtu.model.BaseResult;
import com.vtu.model.Temps;
import com.vtu.service.impl.TempsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/temps")
public class TempsController {

    @Autowired
    TempsServiceImpl tempsService;

    @RequestMapping(value = "/insertTemps")
    public BaseResult insertTemps(@RequestBody Temps temps){
        BaseResult result = tempsService.insert(temps);
        return result;

    }

    /**
     * 而根据用户id 获取未提交的用户模板
     * @return
     */
    @RequestMapping(value = "/getReferTemps")
    public List<Temps> getReferTemps(@RequestBody Temps temps){
        List<Temps> referTemps = tempsService.getReferTemps(temps);
        return referTemps;

    }
}
