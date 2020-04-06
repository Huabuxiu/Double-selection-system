package com.company.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


public class MessageVo {
    private Integer fromId;
    private Integer toId;
    private String message;
    private Date date;


    public MessageVo( Conversation conversation) {
        this.fromId = conversation.getFromid();
        this.toId = conversation.getToid();
        this.message = conversation.getMessage();
        this.date = conversation.getDate();
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
}
