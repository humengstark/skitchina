package com.skitchina.model;

import java.io.Serializable;

/**
 * Created by hu meng on 2017/6/26.
 */
public class DrawMoney implements Serializable {
    private int id;
    private int client_id;
    private String bank;
    private String bankcard;
    private String realname;
    private String draw_time;
    private String remark;

    //0为未处理，1为已打款，2为提现失败
    private int draw_condition;
    private double money;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankcard() {
        return bankcard;
    }

    public void setBankcard(String bankcard) {
        this.bankcard = bankcard;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getDraw_time() {
        return draw_time;
    }

    public void setDraw_time(String draw_time) {
        this.draw_time = draw_time;
    }

    public int getDraw_condition() {
        return draw_condition;
    }

    public void setDraw_condition(int draw_condition) {
        this.draw_condition = draw_condition;
    }
}
