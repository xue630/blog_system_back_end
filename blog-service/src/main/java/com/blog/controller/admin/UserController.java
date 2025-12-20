package com.blog.controller.admin;

import com.blog.dto.UserLoginDTO;
import com.blog.dto.common.UpdateAnnoDTO;
import com.blog.result.Result;
import com.blog.service.CommonService;
import com.blog.service.UserService;
import com.blog.utils.IpUtil;
import com.blog.vo.UserLoginVO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private CommonService commonService;

    private String getClientIp(HttpServletRequest httpServletRequest) {
        return IpUtil.getClientIp(httpServletRequest);
    }

    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO, HttpServletResponse response, HttpServletRequest request) {
        log.info("UserController.java.login方法提示：得到前端发送数据{}",userLoginDTO);
        String ip = getClientIp(request);
        UserLoginVO loginVO = userService.login(userLoginDTO,ip);

        Cookie cookie = new Cookie("auth_token", loginVO.getToken());
        cookie.setPath("/");//设置访问哪些接口时需要带上cookie
        cookie.setHttpOnly(true);//设置仅会话可见
        response.addCookie(cookie);

        loginVO.setToken("******");

        return Result.success(loginVO);
    }

    @PostMapping("/logout")
    public Result<String> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("auth_token", null);
        cookie.setPath("/");
        response.addCookie(cookie);
        return Result.success();
    }

    @PostMapping("/auth")
    public Result<String> auth(){
        log.info("CommonController.auth()提示：前端调用鉴权接口");
        return Result.success();//能穿过拦截器的就是认证通过的
    }
    @PostMapping("/anno")
    public Result<String> updateAnnouncement(@RequestBody UpdateAnnoDTO updateAnnoDTO){
        log.info("公共接口处理修改公告请求");
        commonService.updateAnnouncement(updateAnnoDTO);
        return Result.success();
    }
}
