package com.skitchina.model;

import java.io.Serializable;

/**
 * Created by hu meng on 2017/7/13.
 */
public class ReturnClient implements Serializable {
    private int id;
    private String cellphone;
    private String password;
    private String company_name;
    private String company_address;
    private String company_tel;
    private double balance;
    private String name;
    private int long_id;
    private String station;
    //选择对账时间，0为没有选择，1为星期一和星期四，2为星期二和星期五，3为星期三和星期六
    private int checkTime;

    //0为未设置，1为已设置
    private String draw_password;

    public String getDraw_password() {
        return draw_password;
    }

    public void setDraw_password(String draw_password) {
        this.draw_password = draw_password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_address() {
        return company_address;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }

    public String getCompany_tel() {
        return company_tel;
    }

    public void setCompany_tel(String company_tel) {
        this.company_tel = company_tel;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLong_id() {
        return long_id;
    }

    public void setLong_id(int long_id) {
        this.long_id = long_id;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public int getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(int checkTime) {
        this.checkTime = checkTime;
    }
}
