package com.example.yuan.imagerecognitionmanager.javaBean;

import java.util.List;

/**
 * Created by Yuan on 2017/7/2.
 * class comment:
 */

public class FindLabelsByUserId {

    /**
     * id : 1
     * isNewRecord : false
     * picName : 车站17.jpg
     * url : f6becaa0e1ec4397a8a84596ac97049c.jpg
     * picId : f6becaa0e1ec4397a8a84596ac97049c
     * updateNum : 0
     * errorLabels : ["汽车1","汽车2","旅游地理","汽车3","汽车4","宿舍","中国概念"]
     * succLabels : ["汽车","1","2","3","4"]
     */

    private String id;
    private boolean isNewRecord;
    private String picName;
    private String url;
    private String picId;
    private int updateNum;
    private List<String> errorLabels;
    private List<String> succLabels;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIsNewRecord() {
        return isNewRecord;
    }

    public void setIsNewRecord(boolean isNewRecord) {
        this.isNewRecord = isNewRecord;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public int getUpdateNum() {
        return updateNum;
    }

    public void setUpdateNum(int updateNum) {
        this.updateNum = updateNum;
    }

    public List<String> getErrorLabels() {
        return errorLabels;
    }

    public void setErrorLabels(List<String> errorLabels) {
        this.errorLabels = errorLabels;
    }

    public List<String> getSuccLabels() {
        return succLabels;
    }

    public void setSuccLabels(List<String> succLabels) {
        this.succLabels = succLabels;
    }
}
