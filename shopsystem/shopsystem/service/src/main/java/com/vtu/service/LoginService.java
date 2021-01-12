package com.vtu.service;

import com.vtu.model.Customer;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import java.security.GeneralSecurityException;

public interface LoginService {

    String loginByEmail(String email) throws MessagingException, GeneralSecurityException;

    Customer backFromEmail(String authCode, HttpServletRequest request);
}
