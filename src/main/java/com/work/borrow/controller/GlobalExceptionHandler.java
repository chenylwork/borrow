package com.work.borrow.controller;

import com.work.borrow.po.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 * 继承ResponseEntityExceptionHandler为了解决REST接口的全局异常处理
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 未知异常错误处理
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Message handlerException(Exception e,HttpServletRequest request) {
        e.printStackTrace();
        Message message = Message.createERRMessage();
        return message;
    }
    @ExceptionHandler(RuntimeException.class)
    public Message handlerError(RuntimeException e,HttpServletRequest request) {
        e.printStackTrace();
        Message message = Message.createERRMessage();
        return message;
    }

}
