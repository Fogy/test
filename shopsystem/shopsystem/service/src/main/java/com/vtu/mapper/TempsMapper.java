package com.vtu.mapper;

import com.vtu.model.Temps;

import java.util.List;

public interface TempsMapper {
    int deleteByPrimaryKey(Long tempsId);

    int insert(Temps record);

    int insertSelective(Temps record);

    Temps selectByPrimaryKey(Long tempsId);

    int updateByPrimaryKeySelective(Temps record);

    int updateByPrimaryKey(Temps record);

    List<Temps> getReferTemps(Temps Temps);
}