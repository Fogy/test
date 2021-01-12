package com.vtu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vtu.model.RechargeRecord;
import com.vtu.service.impl.RechargeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RechargeController {
    @Autowired
    RechargeServiceImpl rechargeService;

    @RequestMapping(value = "/getRechargeRecords" ,method = RequestMethod.GET)
    public PageInfo getRechargeRecords(@RequestParam("accountId") Long accountId,
                                       @RequestParam("pageNum") Integer pageNum,
                                       @RequestParam("pageSize") Integer pageSize){

        PageHelper.startPage(pageNum, pageSize);
        List<RechargeRecord> rechargeRecordList = rechargeService.findAllByAccountId(accountId);
        PageInfo pageInfo = new PageInfo(rechargeRecordList);
        return pageInfo;
    }
}
