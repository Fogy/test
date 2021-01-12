package com.vtu.service.impl;

import com.vtu.mapper.TempsMapper;
import com.vtu.model.BaseResult;
import com.vtu.model.Temps;
import com.vtu.service.TempsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TempsServiceImpl implements TempsService {
    @Autowired
    TempsMapper tempsMapper;
    @Override
    public BaseResult insert(Temps record) {
        BaseResult baseResult = new BaseResult();
        int res =  tempsMapper.insert(record);
        if(res>0){
            baseResult.setCode(200);
        }
        return baseResult;
    }

    @Override
    public List<Temps> getReferTemps(Temps temps) {
        List<Temps> listTemps = tempsMapper.getReferTemps(temps);
        return listTemps;
    }
}
