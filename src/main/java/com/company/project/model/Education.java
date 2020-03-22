package com.company.project.model;

import java.util.Date;
import javax.persistence.*;

public class Education {
    @Id
    private Integer eid;

    private Integer sid;

    private String school;

    private String major;

    @Column(name = "time_start")
    private Date timeStart;

    @Column(name = "time_end")
    private Date timeEnd;

    @Column(name = "sedu_dec")
    private String seduDec;

    /**
     * @return eid
     */
    public Integer getEid() {
        return eid;
    }

    /**
     * @param eid
     */
    public void setEid(Integer eid) {
        this.eid = eid;
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
     * @return school
     */
    public String getSchool() {
        return school;
    }

    /**
     * @param school
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     * @return major
     */
    public String getMajor() {
        return major;
    }

    /**
     * @param major
     */
    public void setMajor(String major) {
        this.major = major;
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
     * @return sedu_dec
     */
    public String getSeduDec() {
        return seduDec;
    }

    /**
     * @param seduDec
     */
    public void setSeduDec(String seduDec) {
        this.seduDec = seduDec;
    }
}