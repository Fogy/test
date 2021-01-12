package com.vtu.service.impl;

import com.vtu.mapper.FavMapper;
import com.vtu.model.BaseResult;
import com.vtu.model.Fav;
import com.vtu.service.FavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FavServiceImpl implements FavService {

    @Autowired
    FavMapper favMapper;

    @Override
    public BaseResult addFav(Long accountId, Long worksId) {
        List<Fav> favList = favMapper.selectByAccountIdAndWorksId(accountId, worksId);
        BaseResult baseResult = new BaseResult();
        if (favList == null){
            Fav favNew = new Fav();
            favNew.setAccountId(accountId);
            favNew.setWorksId(worksId);
            favNew.setAddTime(new Date());
            favMapper.insert(favNew);
            baseResult.setCode(200);
            baseResult.setMsg("收藏成功！");
        } else {
            baseResult.setCode(200);
            baseResult.setMsg("已经收藏！");
        }

        return baseResult;
    }

    @Override
    public List<Fav> getFav(Long accountId) {
        List<Fav> favList = favMapper.findAllByAccountId(accountId);
        return favList;
    }

    @Override
    public BaseResult delFav(Long favId) {
        Integer ret = favMapper.deleteByPrimaryKey(favId);
        BaseResult baseResult = new BaseResult();
        baseResult.setValue(ret);
        return baseResult;
    }
}
