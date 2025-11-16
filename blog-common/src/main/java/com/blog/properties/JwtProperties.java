package com.blog.properties;

import jdk.jfr.DataAmount;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "blog.jwt")
@Data
public class JwtProperties {
    private String SecretKey;
    private long TtlMillis;
    private String TokenName;
}
