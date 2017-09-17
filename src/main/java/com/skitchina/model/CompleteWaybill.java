package com.skitchina.model;

import java.io.Serializable;

/**
 * Created by Tony Stark on 2017/9/15.
 */
public class CompleteWaybill implements Serializable {

    private int id;
    private int waybill_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWaybill_id() {
        return waybill_id;
    }

    public void setWaybill_id(int waybill_id) {
        this.waybill_id = waybill_id;
    }
}
