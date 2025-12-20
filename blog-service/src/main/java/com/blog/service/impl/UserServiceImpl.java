package com.blog.service.impl;

import com.blog.constant.MessageConstant;
import com.blog.context.IpLockObjectMap;
import com.blog.dto.UserLoginDTO;
import com.blog.entities.User;
import com.blog.exception.IpLockedException;
import com.blog.exception.UserException;
import com.blog.mapper.UserMapper;
import com.blog.properties.JwtProperties;
import com.blog.properties.LoginProperties;
import com.blog.service.UserService;
import com.blog.utils.JwtUtil;
import com.blog.utils.LoginAttempt;
import com.blog.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;



@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private LoginProperties loginProperties;

    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO,String ip) {

        UserLoginVO userLoginVO = new UserLoginVO();
        User user = userMapper.getUserByName(userLoginDTO.getUsername());
        if(user == null) {
            log.warn("UserServiceImpl.login()提示：未知用户{}尝试登录", userLoginDTO.getUsername());
            throw new UserException(MessageConstant.USER_NAME_EMPTY_EXCEPTION);
        }
        //上方获取用户是否存在


        ConcurrentHashMap<String, LoginAttempt> map = IpLockObjectMap.getLoginMap();
        LoginAttempt loginAttempt = map.get(ip);

        if(loginAttempt!=null&&loginAttempt.isLocked()&& LocalDateTime.now().isBefore(loginAttempt.getLockUntilTime())){//限制对象存在且ip锁定
            //剩余时间计算
            LocalDateTime lockUntilTime = loginAttempt.getLockUntilTime();
            long seconds = Duration.between(LocalDateTime.now(), lockUntilTime).getSeconds();
            log.info("Ip为已锁定状态，剩余解锁时间为{}秒",seconds);
            throw new IpLockedException(MessageConstant.IP_IS_LOCKET + "请在"+seconds+"秒后重试");
        }else if(loginAttempt!=null&&loginAttempt.isLocked()&& LocalDateTime.now().isAfter(loginAttempt.getLockUntilTime())){//限制对象存在但ip锁定但过期
            log.info("检测到限制对象已过期，删除限制对象");
            map.remove(ip);
        }

        //下方判断密码是否正确的逻辑
        if (userLoginDTO.getPassword().equals(user.getPassword())) {
            BeanUtils.copyProperties(user, userLoginVO);
            Map<String, Object> claims = new HashMap<>();
            claims.put("name",user.getName());
            claims.put("id", user.getId());
            String jwt = JwtUtil.createJwt(jwtProperties.getSecretKey(),
                    jwtProperties.getTtlMillis(),
                    claims);
            userLoginVO.setToken(jwt);
            map.remove(ip);
            return userLoginVO;
        }

        LoginAttempt nowLogin = map.get(ip);
        if(nowLogin==null){//对应当前内存中不存在限制对象
            nowLogin = new LoginAttempt();
            nowLogin.setIpAddress(ip);//设置ip
            nowLogin.setFirstAttemptTime(LocalDateTime.now());//设置初次输错时间
            nowLogin.setAttemptCount(1);//设置失败次数
            map.put(ip,nowLogin);//存入内存
            throw new UserException(MessageConstant.PASSWORD_ERROR+
                    ",您还有"
                    +(loginProperties.getAllowNumberOfError()-nowLogin.getAttemptCount())
                    +"次机会");
        }

        //当前内存存在限制对象
        if(nowLogin.getAttemptCount()>=loginProperties.getAllowNumberOfError()){//错误计数器达到5要锁定账户
            nowLogin.setLocked(true);//锁定账户
            nowLogin.setLockUntilTime(LocalDateTime.now().plusMinutes(loginProperties.getLockTime()));//设置解锁时间
            throw new IpLockedException("您的失误次数过多"+MessageConstant.IP_IS_LOCKET+"请在5分钟后重试");
        }else if(nowLogin.getAttemptCount()<=0){//不可能出现的情况，但是还是写上
            throw new IpLockedException("非法错误次数");

        }else{//错误计数器在1-4之间
            if(LocalDateTime.now().isAfter(nowLogin
                    .getFirstAttemptTime().plusMinutes(loginProperties.getLockTime()))) {//次数过期则置0
                log.info("用户预定时间内未错误，重置计数器");
                nowLogin.setAttemptCount(0);
            }
            nowLogin.setAttemptCount(nowLogin.getAttemptCount()+1);
            nowLogin.setFirstAttemptTime(LocalDateTime.now());//重置计数器重新计时
            throw new UserException(MessageConstant.PASSWORD_ERROR+
                    ",您还有"
                    +(loginProperties.getAllowNumberOfError()-nowLogin.getAttemptCount())
                    +"次机会");
        }
    }

    @Override
    public void logout() {

    }
}
