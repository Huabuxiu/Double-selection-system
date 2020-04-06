package com.company.project.model;

import java.util.Date;


public class ConversationVo {
    private String userName;
    private String url;
    private Integer fromId;
    private Integer toId;
    private String message;
    private Date date;
    private String ConversationId;


    public String getConversationId() {
        return ConversationId;
    }

    public void setConversationId(String conversationId) {
        ConversationId = conversationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public Integer getToId() {
        return toId;
    }

    public void setToId(Integer toId) {
        this.toId = toId;
    }
}
