package com.blog.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "blog.login")
@Data
public class LoginProperties {
    private Integer AllowNumberOfError;//允许错误次数
    private Integer LockTime;//错误持续时间
}
