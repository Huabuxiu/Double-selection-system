package com.company.project.model;

import javax.persistence.*;

@Table(name = "voluntary_state")
public class VoluntaryState {
    @Id
    private Integer vid;

    private String alive;

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
     * @return alive
     */
    public String getAlive() {
        return alive;
    }

    /**
     * @param alive
     */
    public void setAlive(String alive) {
        this.alive = alive;
    }
}