package com.wenda.pojo;

import java.util.Date;

public class Question {
    private Integer id;

    private Integer userId;

    private String title;

    private String content;

    private Date createdTime;

    private Integer commentCount;

    public Question(Integer id, Integer userId, String title, String content, Date createdTime, Integer commentCount) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createdTime = createdTime;
        this.commentCount = commentCount;
    }

    public Question() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
}