package com.blog.mapper;

import com.blog.entities.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Mapper
public interface UserMapper {

    @Select("select * from user where username=#{username}")
    User getUserByName(@Param("username") String username);
}
