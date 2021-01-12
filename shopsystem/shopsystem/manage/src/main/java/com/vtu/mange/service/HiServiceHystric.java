package com.vtu.mange.service;

import org.springframework.stereotype.Component;

@Component
public class HiServiceHystric implements HiService {
    @Override
    public String getHi(String name) {
        return "Sorry"+name;
    }
}
