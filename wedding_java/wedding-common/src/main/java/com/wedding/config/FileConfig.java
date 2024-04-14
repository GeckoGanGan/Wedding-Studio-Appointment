package com.wedding.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2023/12/15/15:06
 * @Description:
 */
@Component
@Data
@ConfigurationProperties(prefix = "aliyun.oss.file")
public class FileConfig {
    private  String endpoint;
    private  String keyid;
    private  String keysecret;
    private  String bucketname;
    private String previewurl;
}
