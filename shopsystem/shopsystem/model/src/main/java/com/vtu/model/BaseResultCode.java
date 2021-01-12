package com.vtu.model;

public enum BaseResultCode {
    SUCCESS(200,"Successful operation"),
    ADDRESS_ERROR(404,"Access address error"),
    SERVER_ERROR(500,"Server exception");
    private Integer code;
    private String msg;

    BaseResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
