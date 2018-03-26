package com.example.shorturl.entity;

import java.util.Date;

public class Shorturl {
    private Integer id;

    private String shortCode;

    private String orgUrl;

    private String createTime;

    private String updateTime;

    private Long clickTimes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode == null ? null : shortCode.trim();
    }

    public String getOrgUrl() {
        return orgUrl;
    }

    public void setOrgUrl(String orgUrl) {
        this.orgUrl = orgUrl == null ? null : orgUrl.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Long getClickTimes() {
        return clickTimes;
    }

    public void setClickTimes(Long clickTimes) {
        this.clickTimes = clickTimes;
    }
}