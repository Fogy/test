package com.vtu.service;

import com.vtu.model.BaseResult;
import com.vtu.model.Temps;

import java.util.List;

public interface TempsService {
    BaseResult insert(Temps record);

    List<Temps> getReferTemps(Temps temps);
}
