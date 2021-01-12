package com.vtu.util;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestBc {
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Test
    public void test01(){
        String pass = bCryptPasswordEncoder.encode("123");
        System.out.println(pass);
    }

    @Test
    public void test02(){
        boolean matches = bCryptPasswordEncoder.matches("123", "$2a$10$Ak8J2pD1KZMclvzX3jlQv.RDn2DPlJ90ewBmyD.5ChjtB3gsVwCC6");
        System.out.println(matches);
    }

}
