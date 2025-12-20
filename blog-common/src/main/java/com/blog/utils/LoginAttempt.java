package com.blog.utils;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class LoginAttempt {
    private String ipAddress;      // IP地址
    private int attemptCount;      // 失败次数
    private LocalDateTime firstAttemptTime; // 首次失败时间
    private LocalDateTime lockUntilTime;    // 锁定直到的时间戳
    private boolean isLocked = false;      // 是否已锁定
    public boolean isLocked(){
        return isLocked;
    }
}
