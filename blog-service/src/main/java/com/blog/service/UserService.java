package com.blog.service;

import com.blog.dto.UserLoginDTO;
import com.blog.vo.UserLoginVO;

public interface UserService {
    UserLoginVO login(UserLoginDTO userLoginDTO);

    void logout();
}
