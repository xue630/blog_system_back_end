package com.blog.interceptor;

import com.blog.constant.MessageConstant;
import com.blog.exception.JwtException;
import com.blog.exception.UserException;
import com.blog.properties.JwtProperties;
import com.blog.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class AdminJwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 放行 OPTIONS 预检请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true; // 直接放行
        }
        //return true;//取消拦截测试接口
        Cookie[] cookies = request.getCookies();
        Claims claims = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("auth_token")) {
                    if (!cookie.getValue().isEmpty()) {
                        try {
                            claims = JwtUtil.parseJwt(jwtProperties.getSecretKey(), cookie.getValue());
                        } catch (ExpiredJwtException e) {
                            throw new JwtException(MessageConstant.JWT_TIMEOUT);
                        }
                    }else{
                        throw new JwtException(MessageConstant.JWT_TIMEOUT);
                    }
                }
            }
        }
        if (claims == null) {
            throw new JwtException(MessageConstant.ILLE_JWT);
        }
        Object name = claims.get("name");
        Object id = claims.get("id");
        if (name != null && id != null) {
            log.info("AdminJwtInterceptor提示：jwt串解析成功，欢迎{}", name);
            return "管理员".equals(name);
        }
        throw new UserException(MessageConstant.NOT_ADMIN);
    }
}
