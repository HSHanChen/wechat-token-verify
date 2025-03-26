package com.chansheen.wechat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WeChatConfig {

    @Value("${wechat.token}")
    private String token;

    @Value("${wechat.appId}")
    private String appId;

    @Value("${wechat.appSecret}")
    private String appSecret;

    @Value("${wechat.encodingAesKey}")
    private String encodingAesKey; // 新增字段

    public String getToken() {
        return token;
    }

    public String getAppId() {
        return appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public String getEncodingAesKey() {
        return encodingAesKey; // 新增方法
    }
}
