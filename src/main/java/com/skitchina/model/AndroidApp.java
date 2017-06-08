package com.skitchina.model;

import java.io.Serializable;

/**
 * Created by hu meng on 2017/6/6.
 */

/**
 * 安卓APP 实体类
 */
public class AndroidApp implements Serializable {
    private int id;
    private String name;
    private String version;
    private String url;
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
