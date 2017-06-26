package com.example.yuan.imagerecognitionmanager.javaBean;

/**
 * Created by Yuan on 2017/6/25.
 * class comment:
 */

public class TaskList {
    /**
     * id : e97dc8c974d948b2817acfdd7377d2d1
     * isNewRecord : false
     * createDate : 2017-06-24 01:00:00
     * updateDate : 2017-06-24 01:00:00
     * userid : 1
     * taskSum : 10
     * finishNum : 1
     * finishSpeed : .1
     * name : 超级系统管理员
     */

    private String id;
    private boolean isNewRecord;
    private String createDate;
    private String updateDate;
    private String userid;
    private String taskSum;
    private String finishNum;
    private String finishSpeed;
    private String name;

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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTaskSum() {
        return taskSum;
    }

    public void setTaskSum(String taskSum) {
        this.taskSum = taskSum;
    }

    public String getFinishNum() {
        return finishNum;
    }

    public void setFinishNum(String finishNum) {
        this.finishNum = finishNum;
    }

    public String getFinishSpeed() {
        return finishSpeed;
    }

    public void setFinishSpeed(String finishSpeed) {
        this.finishSpeed = finishSpeed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
