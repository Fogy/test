package com.vtu.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SearchControler {

    @RequestMapping(value = "/searchWorks")
    public String searchWorks(){

        return "/search/search";
    }
}
