package com.vtu.web.service.impl;

import com.vtu.model.BaseResult;
import com.vtu.model.Temps;
import com.vtu.web.service.TempService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TempServiceImpl implements TempService {
    @Override
    public BaseResult insert(Temps temps) {
        return null;
    }

    @Override
    public List<Temps> getReferTemps(Temps temps) {
        return null;
    }
}
