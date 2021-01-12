package com.vtu.mapper;

import com.vtu.model.Manage;
import org.apache.ibatis.annotations.Param;

public interface ManageMapper {
    int deleteByPrimaryKey(Long manageId);

    int insert(Manage record);

    int insertSelective(Manage record);

    Manage selectByPrimaryKey(Long manageId);

    int updateByPrimaryKeySelective(Manage record);

    int updateByPrimaryKey(Manage record);

    /**
     * 查询用户
     * @param manage
     * @return
     */
    Manage findMange(@Param("manage") Manage manage);
}