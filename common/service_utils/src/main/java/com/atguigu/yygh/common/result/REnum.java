package com.atguigu.yygh.common.result;

public enum REnum {

    SUCCESS(20000,true,"成功"),
    Error(20001,false,"失败");

    private REnum(Integer code,Boolean success,String message){
        this.code = code;
        this.success = success;
        this.message = message;
    }

    private Integer code;
    private Boolean success;
    private String message;

    public Integer getCode() {
        return code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}