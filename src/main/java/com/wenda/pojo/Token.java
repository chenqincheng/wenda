package com.wenda.pojo;

import java.util.Date;

public class Token {
    private Integer id;

    private Integer userId;

    private String token;

    private Date expired;

    private Integer status;

    public Token(Integer id, Integer userId, String token, Date expired, Integer status) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.expired = expired;
        this.status = status;
    }

    public Token() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}