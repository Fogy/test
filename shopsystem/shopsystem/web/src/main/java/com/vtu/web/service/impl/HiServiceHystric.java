package com.vtu.web.service.impl;

import com.vtu.web.service.HiService;
import org.springframework.stereotype.Component;

@Component
public class HiServiceHystric implements HiService {
    @Override
    public String getHi(String name) {
        return "Sorry"+name;
    }
}
