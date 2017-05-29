package com.skitchina.model;

/**
 * Created by hu meng on 2017/4/25.
 * 此实体类用来映射MYBATIS返回结果集
 */
public class Achivement {
    private int id;
    private String time4;
    private int user2_id;

    public int getUser2_id() {
        return user2_id;
    }

    public void setUser2_id(int user2_id) {
        this.user2_id = user2_id;
    }

    public String getTime4() {
        return time4;
    }

    public void setTime4(String time4) {
        this.time4 = time4;
    }

    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
