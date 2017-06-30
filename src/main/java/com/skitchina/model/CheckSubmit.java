package com.skitchina.model;

import java.io.Serializable;

/**
 * Created by hu meng on 2017/6/26.
 */
public class CheckSubmit implements Serializable {
    private int id;

    //提交时间
    private String submit_time;

    //提交的订单号字符串拼接
    private String waybill_ids;

    //提交的钱总数
    private double money;

    //该操作的客户id
    private int client_id;

    //该条提交记录的状态，0为未处理，1为已审核通过
    private int condition;

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public String getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(String submit_time) {
        this.submit_time = submit_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWaybill_ids() {
        return waybill_ids;
    }

    public void setWaybill_ids(String waybill_ids) {
        this.waybill_ids = waybill_ids;
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
}
