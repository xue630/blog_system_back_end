package com.blog.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "blog.alioss")
@Data
public class AliOssProperties {
    private String endpoint;
    @Value("${ALIOSS_ACCESS_KEY_ID}")
    private String accessKeyId;
    @Value("${ALIOSS_ACCESS_KEY_SECRET}")
    private String accessKeySecret;
    private String bucketName;
}
