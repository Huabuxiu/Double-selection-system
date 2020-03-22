package com.company.project.model;

import java.util.Date;
import javax.persistence.*;

public class Voluntary {
    @Id
    private Integer vid;

    private Integer sid;

    private Integer rid;

    private Integer tid;

    private Integer level;

    private Integer progress;

    private Date date;

    /**
     * @return vid
     */
    public Integer getVid() {
        return vid;
    }

    /**
     * @param vid
     */
    public void setVid(Integer vid) {
        this.vid = vid;
    }

    /**
     * @return sid
     */
    public Integer getSid() {
        return sid;
    }

    /**
     * @param sid
     */
    public void setSid(Integer sid) {
        this.sid = sid;
    }

    /**
     * @return rid
     */
    public Integer getRid() {
        return rid;
    }

    /**
     * @param rid
     */
    public void setRid(Integer rid) {
        this.rid = rid;
    }

    /**
     * @return tid
     */
    public Integer getTid() {
        return tid;
    }

    /**
     * @param tid
     */
    public void setTid(Integer tid) {
        this.tid = tid;
    }

    /**
     * @return level
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * @param level
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * @return progress
     */
    public Integer getProgress() {
        return progress;
    }

    /**
     * @param progress
     */
    public void setProgress(Integer progress) {
        this.progress = progress;
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
}