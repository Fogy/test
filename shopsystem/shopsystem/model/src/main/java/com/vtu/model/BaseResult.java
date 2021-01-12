package com.vtu.model;

public class BaseResult {
    private Integer code;
    private Object value;
    private String msg;


    public BaseResult(Integer code, Object value) {
        this.code = code;
        this.value = value;
    }

    public BaseResult() {
        this.code = BaseResultCode.SUCCESS.getCode();
        this.msg = BaseResultCode.SUCCESS.getMsg();

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
