package com.blog.context;

import com.blog.utils.LoginAttempt;
import com.blog.utils.MessageAttempt;

import java.util.concurrent.ConcurrentHashMap;

public class IpLockObjectMap {
    public static volatile ConcurrentHashMap<String, LoginAttempt> loginMap ;
    public static volatile ConcurrentHashMap<String, MessageAttempt> msgMap;


    private IpLockObjectMap(){}

    public static ConcurrentHashMap<String, LoginAttempt> getLoginMap(){
        if(loginMap==null){
            synchronized (IpLockObjectMap.class){
                if(loginMap==null){
                    loginMap = new ConcurrentHashMap<>();
                }
            }
        }
        return loginMap;
    }
    public static ConcurrentHashMap<String, MessageAttempt> getMessageMap(){
        if(msgMap==null){
            synchronized (IpLockObjectMap.class){
                if(msgMap==null){
                    msgMap = new ConcurrentHashMap<>();
                }
            }
        }
        return msgMap;
    }
}
