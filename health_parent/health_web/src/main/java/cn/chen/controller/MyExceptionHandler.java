package cn.chen.controller;

import cn.chen.entity.Result;
import cn.chen.exception.MyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 拦截controller抛出的异常
 */
@RestControllerAdvice
public class MyExceptionHandler {
    /**
     * 申明要捕获的异常
     */
    @ExceptionHandler(MyException.class)
    public Result handleMyException(MyException my){
        my.printStackTrace();
        /**
         * 包装返回结果
         */
        return new Result(false,my.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public Result handleException(Exception unknow){
        unknow.printStackTrace();
        /**
         * 包装返回结果
         */
        return new Result(false,"发生异常",unknow.getLocalizedMessage());
    }
}
