package com.example.yuan.imagerecognitionmanager.javaBean;

import java.util.List;

/**
 * Created by Yuan on 2017/6/29.
 * class comment:
 */

public class PicByWord {

    /**
     * picture_name : 97bc847fb4384ab097db111c8ed4f666.jpg
     * finish_time : 2017-06-17T15:51:15Z
     * labels : ["泰山"," 人群"," 女孩"," 泰山景区"]
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
