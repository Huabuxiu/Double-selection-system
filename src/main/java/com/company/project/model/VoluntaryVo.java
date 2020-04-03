package com.company.project.model;

import java.util.Date;

public class VoluntaryVo {
    private   int sid;
    private   int tid;
    private   int level;
    private   int total_score;
    private   String student_name;
    private   String teacher_name;
    private   String exam_type;
    private   String State;
    private   String school;
    private   String major;
    private   int student_uid;
    private   int teacher_uid;

    public int getStudent_uid() {
        return student_uid;
    }

    public void setStudent_uid(int student_uid) {
        this.student_uid = student_uid;
    }

    public int getTeacher_uid() {
        return teacher_uid;
    }

    public void setTeacher_uid(int teacher_uid) {
        this.teacher_uid = teacher_uid;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    private   Date voluntary_time;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTotal_score() {
        return total_score;
    }

    public void setTotal_score(int total_score) {
        this.total_score = total_score;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getExam_type() {
        return exam_type;
    }

    public void setExam_type(String exam_type) {
        this.exam_type = exam_type;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Date getVoluntary_time() {
        return voluntary_time;
    }

    public void setVoluntary_time(Date voluntary_time) {
        this.voluntary_time = voluntary_time;
    }
}
