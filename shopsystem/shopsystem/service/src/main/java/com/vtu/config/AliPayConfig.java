package com.vtu.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.vtu.epay.alipay.AlipayBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AliPayConfig {
    @Value("${appId}")
    private String appId;

    @Value("${gatewayUrl}")
    private String gatewayUrl;

    @Value("${privateKey}")
    private String privateKey;

    @Value("${format}")
    private String format;

    @Value("${charset}")
    private String charset;

    @Value("${publicKey}")
    private String alipayPublicKey;

    @Value("${signType}")
    private String signType;

    @Bean
    public AlipayClient alipayClient(){
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appId, privateKey,
                format, charset, alipayPublicKey, signType);
        return alipayClient;
    }
}
