package com.vtu.web.service.impl;

import com.vtu.model.Customer;
import com.vtu.web.service.LoginService;
import org.springframework.stereotype.Component;

@Component
public class LoginServiceImpl implements LoginService {
    @Override
    public String loginByEmail(String email) {
        return null;
    }

    @Override
    public Customer backFromEmail(String authCode) {
        return null;
    }

}
