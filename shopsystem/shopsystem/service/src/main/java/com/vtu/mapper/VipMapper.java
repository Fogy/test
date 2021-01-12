package com.vtu.mapper;

import com.vtu.model.Vip;
import com.vtu.model.VipDetail;

import java.util.List;

public interface VipMapper {
    int deleteByPrimaryKey(Long vipId);

    int insert(Vip record);

    int insertSelective(Vip record);

    VipDetail selectByPrimaryKey(Long id);

    /**
     * 根据vip类型查询vip
     * @param VipType
     * @return
     */
    List<Vip> selectByVipType(Long VipType);

    int updateByPrimaryKeySelective(Vip record);

    int updateByPrimaryKey(Vip record);
}