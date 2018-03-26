package com.example.shorturl.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = AppException.class)
    @ResponseBody
    public JSONObject jsonErrorHandler(HttpServletRequest req, AppException e) throws Exception {
        JSONObject ret = new JSONObject();
        ret.put("status",e.getStatus());
        ret.put("code",e.getCode());
        ret.put("data",e.getData());
        return ret;
    }
}
