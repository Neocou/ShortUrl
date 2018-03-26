package com.example.shorturl.service;

import com.example.shorturl.utils.Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.example.shorturl.common.Slog;
import com.example.shorturl.common.Sredis;
import com.example.shorturl.dao.ShorturlMapper;
import com.example.shorturl.entity.Shorturl;
import com.example.shorturl.exception.AppException;
import com.example.shorturl.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

@Service
public class UrlService {


    @Autowired
    private ShorturlMapper shorturlMapper;
    @Autowired
    private Slog slog;
    @Autowired
    private Sredis sredis;
    @Value("${shorturl.incrkey}")
    String short_url_index;
    @Value("${shorturl.http_prefix}")
    String http_prefix;


    public  String getOrgUrlByCode(String code) {
        String orgUrl = sredis.getString("short:"+code);
        if(orgUrl == null){
            Shorturl shorturl = shorturlMapper.selectBycode(code);
            if(shorturl !=null){
                orgUrl = shorturl.getOrgUrl();
                final String tempUrl = orgUrl;
                TaskService.submit(() -> {
                    updateShortUrlInfo(code, tempUrl);
                    return 0;
                });
            }
        }
        slog.info("get org url by code:" + code + " org:" + orgUrl);
        return orgUrl;
    }
    public Boolean updateShortUrlInfo(String code, String orgUrl) {
        slog.info("updateShortUrlInfo begin:" + code + " org_url" + orgUrl);
        try {
            if (orgUrl != null) {
                sredis.addString("short:"+code, orgUrl, Constants.REDIS_URL_EXP);
                shorturlMapper.updateUrlStatus(code);
            }
        } catch (Exception ee) {
            slog.error("update url status error:" + code + " url:" + orgUrl);
        }
        return true;
    }

    public Map<String,String> createShortUrl(String orgurl) throws AppException{
        slog.info("origin url_long is " + orgurl);
            Map<String, String> item_map = Maps.newHashMap();
            Map<String, String> db_item = Maps.newHashMap();
            Long id = null;
            String oldCode = sredis.getString(orgurl);
            String code = "";
            String urlDecode = URLDecoder.decode(orgurl);
            if (Utils.checkHttpUrl(urlDecode) == false) {
                slog.info("make short url input not http:" + urlDecode);
                item_map.put("url_short", "");
                item_map.put("url_long", urlDecode);
                item_map.put("type", "0");
                item_map.put("result", "false");
            }
            if (oldCode != null) {
                item_map.put("url_short", http_prefix + oldCode);
                item_map.put("url_long", urlDecode);
                item_map.put("type", "0");
                item_map.put("result", "true");
                slog.info(" get ret_maps from redis :" + item_map);
            } else {
                try {
                    id = sredis.getAndIncr(short_url_index);
                } catch (Exception e) {
                    slog.error("get incre index from redis error:" + orgurl);
                }
                code = Utils.long2H62(id);
                slog.info("now id is " + id + "      transform code is " + code);
                item_map.put("url_short", http_prefix + code );
                item_map.put("url_long", orgurl);
                item_map.put("type", "0");
                item_map.put("result", "true");

                db_item.put("code", code);
                db_item.put("url_long", orgurl);
            }
            try {
                sredis.addString("short:"+code, urlDecode, Constants.REDIS_URL_EXP);
            } catch (Exception ee) {
                slog.error("make shor url save to redis error:" + code + " url:" + orgurl);
            }
        //save to mysql ansyc
        TaskService.submit(() -> {
            saveUrlInfoToDb(db_item);
            return 0;
        });
        return item_map;
    }
    public Boolean saveUrlInfoToDb(Map<String, String> url) throws AppException {
        slog.info("saveUrlInfoToDb begin:" + url.toString());
        if (url == null) {
            return true;
        }
        List<Shorturl> entitys = Lists.newArrayList();
        Shorturl entity = new Shorturl();
        entity.setShortCode(url.get("code"));
        entity.setOrgUrl(url.get("url_long"));
        try {
            shorturlMapper.createUrlsInfo(entity);
        } catch (Exception e) {
            slog.error(" createUrlsInfo error:" + e.getMessage());
        }
        slog.info("saveUrlInfoToDb end:" + url.toString());
        return true;
    }
}
