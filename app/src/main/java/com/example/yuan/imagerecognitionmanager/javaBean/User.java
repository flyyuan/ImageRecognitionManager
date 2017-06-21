package com.example.yuan.imagerecognitionmanager.javaBean;

/**
 * Created by Yuan on 2017/6/20.
 * class comment:
 */

public class User {

    /**
     * isNewRecord : true
     * name : 谢庆端
     * userId : 46be3f92f8774189af929a0a78845e5d
     * writeNum : 5
     * writeSuccNum : 2
     * selectNum : 0
     * selectSuccNum : 0
     */

    private boolean isNewRecord;
    private String name;
    private String userId;
    private int writeNum;
    private int writeSuccNum;
    private int selectNum;
    private int selectSuccNum;

    public boolean isIsNewRecord() {
        return isNewRecord;
    }

    public void setIsNewRecord(boolean isNewRecord) {
        this.isNewRecord = isNewRecord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getWriteNum() {
        return writeNum;
    }

    public void setWriteNum(int writeNum) {
        this.writeNum = writeNum;
    }

    public int getWriteSuccNum() {
        return writeSuccNum;
    }

    public void setWriteSuccNum(int writeSuccNum) {
        this.writeSuccNum = writeSuccNum;
    }

    public int getSelectNum() {
        return selectNum;
    }

    public void setSelectNum(int selectNum) {
        this.selectNum = selectNum;
    }

    public int getSelectSuccNum() {
        return selectSuccNum;
    }

    public void setSelectSuccNum(int selectSuccNum) {
        this.selectSuccNum = selectSuccNum;
    }
}
