package com.skitchina.model;

import java.io.Serializable;

/**
 * Created by hu meng on 2017/4/21.
 */
public class Station implements Serializable {
    private int id;
    private String name;
    private String arrive;

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
