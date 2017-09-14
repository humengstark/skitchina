package com.skitchina.model;

import java.io.Serializable;

/**
 * Created by hu meng on 2017/4/18.
 */
public class Waybill implements Serializable {
    //����ID
    private int id;

    //ҵ��ԱID
    private int user_id;

    //������
    private int waybill_id;

    //ʼ������
    private String origin;

    //Ŀ������
    private String destination;

    //������˾�绰
    private String consignor_tel;

    //�ջ���˾�绰
    private String consignee_tel;

    //������˾
    private String consignor_company;

    //�ջ���˾
    private String consignee_company;

    //������˾��ַ
    private String consignor_address;

    //�ջ���˾��ַ
    private String consignee_address;

    //����
    private double price;

    //�˷�
    private double freight;

    //����
    private int number;

    /**
     * �˷ѵĸ��ʽ
     * 0Ϊ�ָ���1Ϊ����
     */
    private int payway;

    /**
     *�˵�״̬
     *0Ϊ�µ���1Ϊ�����У�2Ϊ�������㣬3Ϊ���տ4Ϊ�ѽ��ˣ�5Ϊ���
     */
    private int condition;

    /**
     * markĬ��Ϊ0���������Ϊ������˫��mark��Ϊ1����Ҫ˫���������Ժ���Ϊ0��
     *
     */
    private int consignor_mark;

    private int consignee_mark;

    public int getConsignee_mark() {
        return consignee_mark;
    }

    public void setConsignee_mark(int consignee_mark) {
        this.consignee_mark = consignee_mark;
    }

    //��ע
    private String remark;

    //�µ�ʱ��
    private String time;

    //�տ���id,���Ϊ0����Ϊδ����
    private int user2_id;

    //0为正常，1为作废，2为审核中
    private int invalid;

    //����״̬��ʱ��
    private String time1;
    private int user1_id;

    private String time2;

    private String time3;

    private String time4;

    private String time5;

    //���˷��˵�ID
    private int user3_id;


    private String user2_time;
    private String user3_time;

    private int client_id;

    //产生随机数作为第二标识
    private int random;

    //对账状态，0为未对账，1为 已扫描，2为审核中，3为对账成功，4为对账失败
    private int check_condition;

    //记录对账人
    private int check_id;

    public int getCheck_condition() {
        return check_condition;
    }

    public void setCheck_condition(int check_condition) {
        this.check_condition = check_condition;
    }

    public int getRandom() {
        return random;
    }

    public void setRandom(int random) {
        this.random = random;
    }

    public int getUser1_id() {
        return user1_id;
    }

    public void setUser1_id(int user1_id) {
        this.user1_id = user1_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }
    //��¼�տ��ʱ��,user2_time�ǵ������߼ĸ����տuser3_time�Ǽĸ��˷�

    public String getUser2_time() {
        return user2_time;
    }

    public void setUser2_time(String user2_time) {
        this.user2_time = user2_time;
    }

    public String getUser3_time() {
        return user3_time;
    }

    public void setUser3_time(String user3_time) {
        this.user3_time = user3_time;
    }

    public int getUser3_id() {
        return user3_id;
    }

    public void setUser3_id(int user3_id) {
        this.user3_id = user3_id;
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

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getTime3() {
        return time3;
    }

    public void setTime3(String time3) {
        this.time3 = time3;
    }

    public String getTime4() {
        return time4;
    }

    public void setTime4(String time4) {
        this.time4 = time4;
    }

    public String getTime5() {
        return time5;
    }

    public void setTime5(String time5) {
        this.time5 = time5;
    }

    public int getUser2_id() {
        return user2_id;
    }

    public void setUser2_id(int user2_id) {
        this.user2_id = user2_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

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

    public int getWaybill_id() {
        return waybill_id;
    }

    public void setWaybill_id(int waybill_id) {
        this.waybill_id = waybill_id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
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

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
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

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public int getConsignor_mark() {
        return consignor_mark;
    }

    public void setConsignor_mark(int consignor_mark) {
        this.consignor_mark = consignor_mark;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
