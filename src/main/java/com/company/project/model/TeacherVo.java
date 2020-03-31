package com.company.project.model;

public class TeacherVo {

    private Integer tid;

    private Integer uid;

    private String name;

    private String title;

    private  int rated;

    private int confirm;

    public TeacherVo(Teacher teacher,int rated, int confirm) {
        this.tid = teacher.getTid();
        this.uid = teacher.getUid();
        this.name = teacher.getName();
        this.title = teacher.getTitle();
        this.rated = rated;
        this.confirm = confirm;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRated() {
        return rated;
    }

    public void setRated(int rated) {
        this.rated = rated;
    }

    public int getConfirm() {
        return confirm;
    }

    public void setConfirm(int confirm) {
        this.confirm = confirm;
    }
}
