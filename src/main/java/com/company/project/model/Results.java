package com.company.project.model;

import javax.persistence.*;

public class Results {
    @Id
    private Integer rid;

    private Integer sid;

    private Integer math;

    private Integer english;

    private Integer politics;

    private Integer major;

    @Column(name = "total_score")
    private Integer totalScore;

    @Column(name = "exam_type")
    private String examType;

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
     * @return math
     */
    public Integer getMath() {
        return math;
    }

    /**
     * @param math
     */
    public void setMath(Integer math) {
        this.math = math;
    }

    /**
     * @return english
     */
    public Integer getEnglish() {
        return english;
    }

    /**
     * @param english
     */
    public void setEnglish(Integer english) {
        this.english = english;
    }

    /**
     * @return politics
     */
    public Integer getPolitics() {
        return politics;
    }

    /**
     * @param politics
     */
    public void setPolitics(Integer politics) {
        this.politics = politics;
    }

    /**
     * @return major
     */
    public Integer getMajor() {
        return major;
    }

    /**
     * @param major
     */
    public void setMajor(Integer major) {
        this.major = major;
    }

    /**
     * @return total_score
     */
    public Integer getTotalScore() {
        return totalScore;
    }

    /**
     * @param totalScore
     */
    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * @return exam_type
     */
    public String getExamType() {
        return examType;
    }

    /**
     * @param examType
     */
    public void setExamType(String examType) {
        this.examType = examType;
    }
}