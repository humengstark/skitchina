package com.skitchina.model;

/**
 * Created by hu meng on 2017/4/17.
 */

/**
 * 返回客户端数据模型
 * code为0，代表请求成功，code为1，代表请求不成功
 * display为0，message为空，display为1，需要填写message
 */
public class ReturnResult {
    private int code;
    private int display;
    private String message;
    private Object data;

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
