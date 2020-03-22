package com.company.project.model;

import javax.persistence.*;

public class User {
    @Id
    private Integer uid;

    @Column(name = "user_role")
    private Integer userRole;

    private String username;

    private String password;

    private String token;

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
     * @return user_role
     */
    public Integer getUserRole() {
        return userRole;
    }

    /**
     * @param userRole
     */
    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }
}