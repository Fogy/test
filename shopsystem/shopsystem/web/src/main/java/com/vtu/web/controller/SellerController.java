package com.vtu.web.controller;

import com.vtu.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SellerController {

    private static Logger logger = LoggerFactory.getLogger(SellerController.class);
    /**
     * 跳转到卖家中心
     * @return
     */
    @RequestMapping(value = "/goSeller")
    public String goSeller(Model model, HttpServletRequest request){
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        model.addAttribute("customer",customer);
        return "seller/seller";
    }



}
