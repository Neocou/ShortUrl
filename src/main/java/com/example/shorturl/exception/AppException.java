package com.example.shorturl.exception;

import com.example.shorturl.utils.Constants;

public class AppException extends Exception{

    private int status = Constants.APP_ERROR_STATUS;
    private String code;
    private Object data;

    public AppException( int status, String code, Object data) {
        super((String)data);
        this.status = status;
        this.code = code;
        this.data = data;
    }
    public AppException(  String code, Object data) {
        super((String)data);
        this.code = code;
        this.data = data;
    }
    public AppException(String code) {
        super("");
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
