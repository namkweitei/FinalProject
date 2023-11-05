package com.example.asm;

import java.io.Serializable;

public class ObservationModel implements Serializable {
    private int id;
    private String observation;
    private String time;
    private String comments;
    private int hikeId;

    public ObservationModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getHikeId() {
        return hikeId;
    }

    public void setHikeId(int hikeId) {
        this.hikeId = hikeId;
    }
    @Override
    public String toString() {
        return "Hike{" +
                "id=" + id +
                ", name='" + observation + '\'' +
                ", time='" + time + '\'' +
                ", comments='" + comments + '\'' +
                ", hikeId='" + hikeId + '\'' +
                '}';
    }
}