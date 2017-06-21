package com.example.yuan.imagerecognitionmanager.javaBean;

import java.util.List;

/**
 * Created by Yuan on 2017/6/16.
 * class comment:
 */

public class PictureTab {

    /**
     * picture_name : ec7e5878da40406695a86c37749d0c82.jpg
     * finish_time : Sat Jun 17 14:37:25 CST 2017
     * labels : ["车站"," 和谐号"," 售票处"," 人群"]
     */

    private String picture_name;
    private String finish_time;
    private List<String> labels;

    public String getPicture_name() {
        return picture_name;
    }

    public void setPicture_name(String picture_name) {
        this.picture_name = picture_name;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
}
