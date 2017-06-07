package com.skitchina.model;

import java.io.Serializable;

/**
 * Created by hu meng on 2017/6/6.
 */

/**
 * 安卓APP 实体类
 */
public class AndroidApp implements Serializable {
    private String version;
    private String address;
    private String time;
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
