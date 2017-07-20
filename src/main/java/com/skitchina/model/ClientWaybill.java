package com.skitchina.model;

import java.io.Serializable;

/**
 * Created by hu meng on 2017/6/20.
 */

/**
 * 用户下的单子  实体类
 */
public class ClientWaybill implements Serializable {
    private int id;
    private int client_id;
    private int user_id;
    private String time1;
    private int origin;
    private int destination;
    private String consignor_tel;
    private String consignee_tel;
    private String consignor_company;
    private String consignee_company;
    private String consignor_address;
    private String consignee_address;
    private double price;
    private int number;
    private int payway;
    private String remark;
    private int invalid;
    private int waybill_id;

    //0为下单，1为已接单，2为已完成
    private int condition;

    public int getWaybill_id() {
        return waybill_id;
    }

    public void setWaybill_id(int waybill_id) {
        this.waybill_id = waybill_id;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public String getConsignor_tel() {
        return consignor_tel;
    }

    public void setConsignor_tel(String consignor_tel) {
        this.consignor_tel = consignor_tel;
    }

    public String getConsignee_tel() {
        return consignee_tel;
    }

    public void setConsignee_tel(String consignee_tel) {
        this.consignee_tel = consignee_tel;
    }

    public String getConsignor_company() {
        return consignor_company;
    }

    public void setConsignor_company(String consignor_company) {
        this.consignor_company = consignor_company;
    }

    public String getConsignee_company() {
        return consignee_company;
    }

    public void setConsignee_company(String consignee_company) {
        this.consignee_company = consignee_company;
    }

    public String getConsignor_address() {
        return consignor_address;
    }

    public void setConsignor_address(String consignor_address) {
        this.consignor_address = consignor_address;
    }

    public String getConsignee_address() {
        return consignee_address;
    }

    public void setConsignee_address(String consignee_address) {
        this.consignee_address = consignee_address;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPayway() {
        return payway;
    }

    public void setPayway(int payway) {
        this.payway = payway;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getInvalid() {
        return invalid;
    }

    public void setInvalid(int invalid) {
        this.invalid = invalid;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }
}
