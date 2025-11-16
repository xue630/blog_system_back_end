package com.blog.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Slf4j
public class JwtUtil {


    public static String createJwt(String SecreteKey,
                                   long TtlMillis,
                                   Map<String,Object> claims){
        // 创建密钥
        SecretKey key = Keys.hmacShaKeyFor(SecreteKey.getBytes(StandardCharsets.UTF_8));

        // 计算过期时间
        long exp = System.currentTimeMillis() + TtlMillis;
        Date expDate = new Date(exp);

        // 构建 JWT - 新版 API
        String jwt = Jwts.builder()
                .claims(claims)  // 新版设置 claims 的方法
                .expiration(expDate)
                .signWith(key)   // 新版签名方法，自动检测算法
                .compact();

        log.info("JwtUtil.java提示：Jwt串创建完成");
        return jwt;
    }

    public static Claims parseJwt(String SecreteKey,String token){
        // 创建密钥
        SecretKey key = Keys.hmacShaKeyFor(SecreteKey.getBytes(StandardCharsets.UTF_8));

        // 解析 JWT - 新版 API
        return Jwts.parser()
                .verifyWith(key)  // 新版验证方法
                .build()
                .parseSignedClaims(token)  // 新版解析方法
                .getPayload();  // 获取 Claims
    }
}
