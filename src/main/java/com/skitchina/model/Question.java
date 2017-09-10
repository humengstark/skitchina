package com.skitchina.model;

import java.io.Serializable;

/**
 * Created by Tony Stark on 2017/9/10.
 */
public class Question implements Serializable {
    private int id;
    private String title;
    private String content;
    private String question_time;

    public String getQuestion_time() {
        return question_time;
    }

    public void setQuestion_time(String question_time) {
        this.question_time = question_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
