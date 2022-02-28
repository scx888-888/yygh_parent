package com.atguigu.yygh.common.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class R {

    private Integer code;//返回码

    private Boolean success;//是否成功

    private String message;//返回消息

    private Map<String, Object> data = new HashMap<> ();//返回数据

    private R() {}

    public static R ok() {
        R r = new R();
        r.setCode(REnum.SUCCESS.getCode ());
        r.setSuccess(REnum.SUCCESS.getSuccess ());
        r.setMessage(REnum.SUCCESS.getMessage ());
        return r;
    }


    public static R error() {
        R r = new R();
        r.setCode(REnum.Error.getCode ());
        r.setSuccess(REnum.Error.getSuccess ());
        r.setMessage(REnum.Error.getMessage ());
        return r;
    }

    public R code(Integer code) {
        this.setCode (code);
        return this;
    }

    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    public R success(Boolean flag) {
        this.setSuccess(flag);
        return this;
    }

    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String,Object> map){
        this.data = map;
        return this;
    }
}