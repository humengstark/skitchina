package com.skitchina.model;

/**
 * Created by hu meng on 2017/4/17.
 */

/**
 * ���ؿͻ�������ģ��
 * codeΪ0����������ɹ���codeΪ1���������󲻳ɹ�
 * displayΪ0��messageΪ�գ�displayΪ1����Ҫ��дmessage
 */
public class ReturnResult {
    private int code;
    private int display;
    private String message;
    private Object data;

    public ReturnResult() {
    }

    public ReturnResult(int code) {
        this.code = code;
        this.display = 0;
        this.message = "";
        this.data = "";
    }

    public ReturnResult(int code, int display, String message, Object data) {
        this.code = code;
        this.display = display;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
