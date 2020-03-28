package com.company.project.model;

import javax.persistence.*;

public class Teacher {
    @Id
    private Integer tid;

    private Integer uid;

    private String name;

    private String title;

    @Column(name = "research_direction")
    private String researchDirection;

    private String image;

    @Column(name = "e_mail")
    private String eMail;

    @Column(name = "research_findings")
    private String researchFindings;

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
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return research_direction
     */
    public String getResearchDirection() {
        return researchDirection;
    }

    /**
     * @param researchDirection
     */
    public void setResearchDirection(String researchDirection) {
        this.researchDirection = researchDirection;
    }

    /**
     * @return image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return e_mail
     */
    public String geteMail() {
        return eMail;
    }

    /**
     * @param eMail
     */
    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    /**
     * @return research_findings
     */
    public String getResearchFindings() {
        return researchFindings;
    }

    /**
     * @param researchFindings
     */
    public void setResearchFindings(String researchFindings) {
        this.researchFindings = researchFindings;
    }
}