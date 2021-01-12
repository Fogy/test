package com.vtu.web.controller;

import com.vtu.model.Customer;
import com.vtu.web.config.AliyunOSSUtil;
import com.vtu.web.service.CustomerService;
import com.vtu.web.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserProfileController {

    @Autowired
    CustomerService customerService;

    @Autowired
    SellerController sellerController;

    /**
     * 跳转到个人中心
     * @return
     */
    @RequestMapping("/goUserProfile")
    public String goUserProfile(Model model,HttpServletRequest request){
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        model.addAttribute("customer",customer);
        return "userProfile/user_profile";
    }

    @RequestMapping(value = "/editCustomer",method = RequestMethod.POST,consumes = "application/json")
    @ResponseBody
    public String saveUser(@RequestBody Customer customer, HttpServletRequest request){
        Customer c = (Customer) request.getSession().getAttribute("customer");
        c.setName(customer.getName());
        c.setSex(customer.getSex());
        c.setPhone(customer.getPhone());
        c.setEmail(customer.getEmail());
        customerService.editCustomer(c);
        return "success";
    }

    /**
     * 上传文件
     * @param request
     * @param file
     * @return
     * @throws IllegalStateException
     */
    @PostMapping("/upload")
    public String upload(HttpServletRequest request,@RequestParam("file") MultipartFile file,Model model,String toPage){
        try {
            String fileUrl = AliyunOSSUtil.upload(FileUtil.multipartFileToFile(file));
            // 入库
            Customer customer = (Customer) request.getSession().getAttribute("customer");
            customer.setHeadPortraitUrl(fileUrl);
            customerService.editCustomer(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(toPage.equals("person")){
            return goUserProfile(model,request);
        }else if(toPage.equals("seller")){
            return sellerController.goSeller(model,request);
        }
        return goUserProfile(model,request);
    }
}
