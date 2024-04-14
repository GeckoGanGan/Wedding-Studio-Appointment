package com.wedding.config.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/02/03/19:16
 * @Description:
 */
@ConfigurationProperties(prefix = "aliyun.sms")
@Component
@Data
public class AliyunSmsConfig {
    public String product;
    public String domain;
    public String accessKeyId;
    public String accessKeySecret;
    public String templateCode;
    public String signatureName;
    public String reginId;
}
