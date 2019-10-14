package cn.chen.controller;

/**
 * @author haixin
 * @time 2019-10-14
 */

import cn.chen.entity.Result;
import cn.chen.exception.MyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 拦截controller抛出的异常
 */
@RestControllerAdvice
public class MyExceptionHandler {

    //声明要捕获的异常类型
    @ExceptionHandler(MyException.class)
    public Result handleMyException(MyException my){
        my.printStackTrace();
        // 包装一下返回的结果
        return new Result(false, my.getLocalizedMessage());
    }

    //声明要捕获的异常类型
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception unknown){
        unknown.printStackTrace();
        // 包装一下返回的结果
        return new Result(false, "发生异常了");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Result noAccessHandler(AccessDeniedException accessDeniedException){

        return new Result(false, "权限不足");
    }
}

