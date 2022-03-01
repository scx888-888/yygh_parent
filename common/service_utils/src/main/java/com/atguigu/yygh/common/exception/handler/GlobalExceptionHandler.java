package com.atguigu.yygh.common.exception.handler;

import com.atguigu.yygh.common.exception.YyghException;
import com.atguigu.yygh.common.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author scx
 * @Date 2022/3/1 10:38
 * @Description 统一异常处理类
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public R error(Exception e){
        //e.printStackTrace();
        log.error(e.getMessage());
        return R.error();
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        //e.printStackTrace();
        log.error(e.getMessage());
        return R.error().message("算数逻辑异常异常");
    }

    @ExceptionHandler(YyghException.class)
    @ResponseBody
    public R error(YyghException e){
        //e.printStackTrace();
        log.error(e.getMessage());
        return R.error().message(e.getMsg()).code(e.getCode());
    }
}
