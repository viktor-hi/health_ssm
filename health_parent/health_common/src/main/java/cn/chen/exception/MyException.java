package cn.chen.exception;

/**
 * @author haixin
 * @time 2019-10-13
 * 自定义异常：终止已经不符合业务逻辑的操作
 */
public class MyException extends RuntimeException {
    public MyException(String message){
        super(message);
    }
}