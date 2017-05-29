package com.skitchina.model;

import java.io.Serializable;

/**
 * Created by hu meng on 2017/4/18.
 */

/**
 * 常用联系人
 */
public class Contact implements Serializable {
    private int id;

    private int user_id;

    private String cellphone;

    private String company;

    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
