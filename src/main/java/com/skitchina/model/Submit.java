package com.skitchina.model;

import java.io.Serializable;

/**
 * Created by hu meng on 2017/4/25.
 */
public class Submit implements Serializable {
    private int id;
    private int user2_id;
    private double achievement;
    private String time4;
    private String name;
    //只交运费的单号
    private String freight;
    //只交货款的单号
    private String price;
    //货款和运费一起交的单号
    private String freightandprice;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFreightandprice() {
        return freightandprice;
    }

    public void setFreightandprice(String freightandprice) {
        this.freightandprice = freightandprice;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser2_id() {
        return user2_id;
    }

    public void setUser2_id(int user2_id) {
        this.user2_id = user2_id;
    }

    public double getAchievement() {
        return achievement;
    }

    public void setAchievement(double achievement) {
        this.achievement = achievement;
    }

    public String getTime4() {
        return time4;
    }

    public void setTime4(String time4) {
        this.time4 = time4;
    }
}
