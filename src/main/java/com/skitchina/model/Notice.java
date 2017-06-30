package com.skitchina.model;

import java.io.Serializable;

/**
 * Created by hu meng on 2017/6/27.
 */
public class Notice implements Serializable {
    private int id;
    private String time;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
