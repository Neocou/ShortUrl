package com.example.shorturl.dao;

import com.example.shorturl.entity.Shorturl;
import org.springframework.stereotype.Component;


@Component
public interface ShorturlMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shorturl record);

    int insertSelective(Shorturl record);

    Shorturl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shorturl record);

    int updateByPrimaryKey(Shorturl record);

    Shorturl selectBycode(String code);

    void updateUrlStatus(String code);
    void createUrlsInfo(Shorturl entity);
}