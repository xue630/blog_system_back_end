package com.blog.handler;


import com.blog.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.blog.result.Result;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public Result<String> handleException(BaseException ex){
        log.error("GlobalExceptionHandler.java提醒：{}",ex.getMessage());
        return Result.error(ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> handleException(MethodArgumentNotValidException ex){
        log.error("GlobalExceptionHandler.java提醒(参数检查失败)：{}",ex.getMessage());
        return Result.error(ex.getMessage());
    }
}
