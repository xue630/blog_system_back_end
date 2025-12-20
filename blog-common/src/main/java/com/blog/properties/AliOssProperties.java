package com.blog.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "blog.alioss")
@Data
public class AliOssProperties {
    @Value("${ALIOSS_ACCESS_ENDPOINT}")
    private String endpoint;
    @Value("${ALIOSS_ACCESS_KEY_ID}")
    private String accessKeyId;
    @Value("${ALIOSS_ACCESS_KEY_SECRET}")
    private String accessKeySecret;
    @Value("${ALIOSS_ACCESS_BUCKETNAME}")
    private String bucketName;
}
