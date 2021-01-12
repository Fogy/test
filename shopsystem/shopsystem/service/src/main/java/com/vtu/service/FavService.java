package com.vtu.service;

import com.vtu.model.BaseResult;
import com.vtu.model.Fav;

import java.util.List;

public interface FavService {
    BaseResult addFav(Long accountId, Long worksId);
    List<Fav> getFav(Long accountId);
    BaseResult delFav(Long favId);
}
