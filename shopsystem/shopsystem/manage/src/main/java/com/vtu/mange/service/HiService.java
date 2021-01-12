package com.vtu.mange.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service", fallback = HiServiceHystric.class)
public interface HiService {
    @RequestMapping(value = "/getHi",method = RequestMethod.GET)
    String getHi(@RequestParam(value = "name") String name);
}
