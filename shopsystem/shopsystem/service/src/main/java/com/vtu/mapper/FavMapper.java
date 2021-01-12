package com.vtu.mapper;


import com.vtu.model.Fav;

import java.util.List;

public interface FavMapper {
    int deleteByPrimaryKey(Long favId);

    int insert(Fav record);

    int insertSelective(Fav record);

    Fav selectByPrimaryKey(Long favId);

    List<Fav> selectByAccountIdAndWorksId(Long accountId, Long worksId);

    int updateByPrimaryKeySelective(Fav record);

    int updateByPrimaryKey(Fav record);

    List<Fav> findAllByAccountId(Long accountId);
}