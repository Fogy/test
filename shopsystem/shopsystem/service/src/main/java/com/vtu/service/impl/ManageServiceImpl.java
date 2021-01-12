package com.vtu.service.impl;

import com.vtu.mapper.ManageMapper;
import com.vtu.model.Manage;
import com.vtu.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManageServiceImpl implements ManageService {

    @Autowired
    ManageMapper manageMapper;

    @Override
    public Manage findMange(Manage manage) {
        Manage man = null;
        if(null != manage && null != manage.getManageCount()){
            man = manageMapper.findMange(manage);
        }
        return man;
    }
}
