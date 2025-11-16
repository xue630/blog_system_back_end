package com.blog.service.impl;

import com.blog.constant.MessageConstant;
import com.blog.dto.UserLoginDTO;
import com.blog.entities.User;
import com.blog.exception.UserException;
import com.blog.mapper.UserMapper;
import com.blog.properties.JwtProperties;
import com.blog.service.UserService;
import com.blog.utils.JwtUtil;
import com.blog.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO) {

        UserLoginVO userLoginVO = new UserLoginVO();
        User user = userMapper.getUserByName(userLoginDTO.getUsername());
        if(user == null) {
            log.warn("UserServiceImpl.login()提示：未知用户{}尝试登录", userLoginDTO.getUsername());
            throw new UserException(MessageConstant.USER_NAME_EMPTY_EXCEPTION);
        }
        if (userLoginDTO.getPassword().equals(user.getPassword())) {
            BeanUtils.copyProperties(user, userLoginVO);
            Map<String, Object> claims = new HashMap<>();
            claims.put("name",user.getName());
            claims.put("id", user.getId());
            String jwt = JwtUtil.createJwt(jwtProperties.getSecretKey(),
                    jwtProperties.getTtlMillis(),
                    claims);
            userLoginVO.setToken(jwt);
            return userLoginVO;
        }
        throw new UserException(MessageConstant.PASSWORD_ERROR);
    }

    @Override
    public void logout() {

    }
}
