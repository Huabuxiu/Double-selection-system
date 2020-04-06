package com.company.project.model;

import java.util.Date;
import javax.persistence.*;

public class Conversation {
    @Id
    private String conid;

    private Integer fromid;

    private Integer toid;

    private String message;

    private Date date;

    private String state;

    /**
     * @return conid
     */
    public String getConid() {
        return conid;
    }

    /**
     * @param conid
     */
    public void setConid(String conid) {
        this.conid = conid;
    }

    /**
     * @return fromid
     */
    public Integer getFromid() {
        return fromid;
    }

    /**
     * @param fromid
     */
    public void setFromid(Integer fromid) {
        this.fromid = fromid;
    }

    /**
     * @return toid
     */
    public Integer getToid() {
        return toid;
    }

    /**
     * @param toid
     */
    public void setToid(Integer toid) {
        this.toid = toid;
    }

    /**
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }
}