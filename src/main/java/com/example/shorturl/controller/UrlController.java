package com.example.shorturl.controller;


import com.example.shorturl.exception.AppException;
import com.example.shorturl.service.UrlService;
import com.example.shorturl.utils.CodeChecking;
import com.example.shorturl.utils.Constants;
import com.example.shorturl.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import com.example.shorturl.common.Slog;

import java.net.URI;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/short" , produces = MediaType.APPLICATION_JSON_VALUE)
public class UrlController {
    @Autowired
    private Slog slog;
    @Autowired
    private UrlService urlService;

    @GetMapping("/{urlcode}")
    public ResponseEntity redirectUrl(@PathVariable("urlcode") String code) throws AppException{
        slog.info("redirect url begin:"+ code);
        if (!CodeChecking.checking(code, Constants.CODE_LENGTH_MAX)){
            throw new AppException("0","code format error");
        }
        String orgUrl = urlService.getOrgUrlByCode(code);
        if (orgUrl == null){
            throw  new AppException("0","cant find org url by code :"+code);
        }
        return ResponseEntity.ok(Utils.getRespons(orgUrl));
    }
    @GetMapping("/get/{orgurl}")
    public ResponseEntity createUrl(@PathVariable("orgurl")String orgurl) throws AppException{
        slog.info("short url create begin:"+orgurl);
        Map<String ,String> shortUrl = urlService.createShortUrl(orgurl);
        return ResponseEntity.ok(Utils.getRespons(shortUrl));
    }

}
