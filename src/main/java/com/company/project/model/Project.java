package com.company.project.model;

import java.util.Date;
import javax.persistence.*;

public class Project {
    @Id
    private Integer pid;

    private Integer uid;

    private String name;

    private String position;

    @Column(name = "time_start")
    private Date timeStart;

    @Column(name = "time_end")
    private Date timeEnd;

    private String describes;

    /**
     * @return pid
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * @return uid
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * @param uid
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return time_start
     */
    public Date getTimeStart() {
        return timeStart;
    }

    /**
     * @param timeStart
     */
    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    /**
     * @return time_end
     */
    public Date getTimeEnd() {
        return timeEnd;
    }

    /**
     * @param timeEnd
     */
    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    /**
     * @return describes
     */
    public String getDescribes() {
        return describes;
    }

    /**
     * @param describes
     */
    public void setDescribes(String describes) {
        this.describes = describes;
    }
}