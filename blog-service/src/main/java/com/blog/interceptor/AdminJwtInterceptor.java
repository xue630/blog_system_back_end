package com.blog.interceptor;

import com.blog.constant.MessageConstant;
import com.blog.exception.JwtException;
import com.blog.exception.UserException;
import com.blog.properties.JwtProperties;
import com.blog.utils.IpUtil;
import com.blog.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.security.auth.login.LoginException;

@Component
@Slf4j
public class AdminJwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    @Value("${AllowIp}")//用于从配置文件获取白名单
    private String AllowIp;


    private String getClientIp(HttpServletRequest httpServletRequest) {
        return IpUtil.getClientIp(httpServletRequest);
    }
    private boolean IpIsAllow(String Ipv4Ip){
        return Ipv4Ip.substring(0, Ipv4Ip.lastIndexOf('.')).equals(AllowIp.substring(0, Ipv4Ip.lastIndexOf('.')));
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 放行 OPTIONS 预检请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true; // 直接放行
        }
        String requestIP = getClientIp(request);

         //处理IPv6地址
        if ("0:0:0:0:0:0:0:1".equals(requestIP) || "::1".equals(requestIP)) {
            // 如果是IPv6的localhost地址，转换为IPv4
            requestIP = "127.0.0.1";
        }

        log.info("管理员拦截器执行ip检查，预检Ip={}，AllowIp={}",requestIP,AllowIp);

        if(!IpIsAllow(requestIP)){
            throw new UserException(MessageConstant.ADMIN_ILL_IP);
        }


//       return true;//取消拦截测试接口
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
        if (id != null && "管理员".equals(name)) {
            log.info("AdminJwtInterceptor提示：jwt串解析成功，欢迎{}", name);
            return true;
        }
        throw new UserException(MessageConstant.NOT_ADMIN);
    }
}
