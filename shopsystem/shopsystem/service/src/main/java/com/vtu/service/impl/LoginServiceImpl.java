package com.vtu.service.impl;

import com.sun.mail.util.MailSSLSocketFactory;
import com.vtu.mapper.CustomerMapper;
import com.vtu.mapper.CustomerVipMapper;
import com.vtu.model.Customer;
import com.vtu.model.CustomerVip;
import com.vtu.service.LoginService;
import com.vtu.utils.GenerateRandomCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.beans.Transient;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

@Service
public class LoginServiceImpl implements LoginService {

    @Value("${home.path}")
    private String homePath;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    CustomerVipMapper customerVipMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Transient
    @Override
    public String loginByEmail(String email) throws MessagingException, GeneralSecurityException {

        String authCode = "";

        Customer customer = customerMapper.selectByEmail(email);
        if (customer == null){
            Customer customerNew = new Customer();
            customerNew.setEmail(email);
            customerNew.setIsAuth("false");

            while (true){
                authCode = GenerateRandomCode.getCode(32);
                Customer tCustomer = customerMapper.selectByAuthCode(authCode);
                if (tCustomer == null){
                    customerNew.setAuthCode(authCode);
                    break;
                }
            }

            customerNew.setAccount("VT" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
            customerNew.setEmailSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()));
            customerNew.setName("视频大师");
            customerMapper.insert(customerNew);
        } else {
            customer.setEmail(email);
            while (true){
                authCode = GenerateRandomCode.getCode(32);
                Customer tCustomer = customerMapper.selectByAuthCode(authCode);
                if (tCustomer == null){
                    customer.setAuthCode(authCode);
                    customer.setEmailSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()));
                    break;
                }
            }
            customerMapper.updateByPrimaryKey(customer);
        }

        //发送邮件
        String from = "dev0010@163.com";
        String host = "smtp.163.com";

        Properties properties = System.getProperties();
        /*properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.auth", "true");*/

        /*Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.socketFactory.port", "465");
        //properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
*/
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");


        /*// SSL加密
        MailSSLSocketFactory sf = null;
        sf = new MailSSLSocketFactory();
        // 设置信任所有的主机
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);*/


        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "dev007");
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject("账号激活");
        String content = homePath+"back/email/" + authCode;
        message.setContent(content, "text/html;charset=UTF-8");
        Transport.send(message);

        logger.info("邮件发送成功");
        return "success";
    }

    @Transient
    @Override
    public Customer backFromEmail(String authCode, HttpServletRequest request) {
        Customer customer = customerMapper.selectByAuthCode(authCode);
        if (customer == null){
            return null;
        } else {
            long nowTime = System.currentTimeMillis();
            long sendEmailTime = 0;
            try {
                sendEmailTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").parse(customer.getEmailSendTime()).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
//            if (nowTime - sendEmailTime >  10 * 60 * 1000){
//                customer.setIsAuth("false");
//                customer.setAuthCode(null);
//                return "链接超时！";
//            }
            customer.setIsAuth("true");
//            customer.setAuthCode(null);

            CustomerVip customerVip =  customerVipMapper.selectByAccountId(customer.getAccountId());
            customer.setCustomerVip(customerVip);

            customerMapper.updateByPrimaryKey(customer);
            request.setAttribute("customer", customer);
            return customer;
        }
    }
}
