package com.skitchina.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hu meng on 2017/6/27.
 */
public class Advertisement implements Serializable{


    private int id;
    private int client_id;
    private String simpleIntroduce;
    private String introduce;
    private int imgNum;

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getSimpleIntroduce() {
        return simpleIntroduce;
    }

    public void setSimpleIntroduce(String simpleIntroduce) {
        this.simpleIntroduce = simpleIntroduce;
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

    public int getImgNum() {
        return imgNum;
    }

    public void setImgNum(int imgNum) {
        this.imgNum = imgNum;
    }

    public String getFirstImgPath() {
        String path = "http://123.206.24.66:8686/clientImgs/" + client_id + "/0.jpg";
//        String path = "http://192.168.0.124:8080/clientImgs/"+client_id+"/0.jpg";
        return path;
    }

    public String getSecondImgPath() {
        String path = "http://123.206.24.66:8686/clientImgs/"+client_id+"/1.jpg";
//        String path = "http://192.168.0.124:8080/clientImgs/"+client_id+"/1.jpg";
        return path;
    }

    public List<String> getOtherImgsPath() {
        List<String> paths = new ArrayList<String>();
        for (int i=2;i<imgNum;i++) {
            String path = "http://123.206.24.66:8686/clientImgs/" +client_id+"/"+ i + ".jpg";
//            String path = "http://192.168.0.124:8080/clientImgs/" +client_id+"/"+ i + ".jpg";
            paths.add(path);
        }
        return paths;
    }
}
