package com.atguigu.servicebase.config;

import com.atguigu.commonutils.GuliException;
import com.atguigu.commonutils.LitaException;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * 统一异常处理类
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    //@ExceptionHandler(Exception.class)
    //@ResponseBody
    //public R error(Exception e) {
    //    e.printStackTrace();
    //    log.error(ExceptionUtil.getMessage(e));
    //    return R.error().message("系统繁忙请稍后再试(全局)");
    //}

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public R nullError(Exception e) {
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return R.error().message("系统繁忙请稍后再试(空异常)");
    }

    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R guliError(GuliException e) {
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return R.error().message(e.getMsg()).code(e.getCode());
    }

    @ExceptionHandler(LitaException.class)
    @ResponseBody
    public R litaError(LitaException e) {
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return R.error().message(e.getMsg()).code(e.getCode());
    }

}