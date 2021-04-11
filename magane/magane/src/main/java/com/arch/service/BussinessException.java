package com.arch.service;

public class BussinessException extends RuntimeException {
    private static final long serialVersionUID = 2332608236621015980L;
    /**
     * 用于存放后端返回的数据
     **/
    private Object data;

    private String msg;

    public BussinessException(Throwable cause) {
        super(cause);
    }

    public BussinessException(String message) {
        super(message);
    }

    public BussinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getMsg() {
        return this.msg;
    }
}
