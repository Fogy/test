package com.vtu.web.util;

import org.junit.Test;

public class OtherTest {

    @Test
    public void test1(){
        String url = "https://vtuweb.oss-cn-beijing.aliyuncs.com/video/videoOutput/1577544575601/preview/image_00006.png";
        url = url.substring(0,url.lastIndexOf("/")+1);
        System.out.println(url);
    }

    @Test
    public void test2(){
        String s="aa.jsp";
        System.out.println(s.substring(s.lastIndexOf(".")+1));
    }


}
