package com.example.yuan.imagerecognitionmanager.javaBean;

/**
 * Created by Yuan on 2017/6/20.
 * class comment:
 */

public class User {

    /**
     * id : 8db0f850f17e49f291339a684c5bb174
     * isNewRecord : false
     * name : duang conputer1111
     * userId : 8db0f850f17e49f291339a684c5bb174
     * photo : /xxzx/userfiles/1/images/photo/2017/06/8a16d0830a46d491ca9dfd257367269f.jpg
     * writeNum : 8
     * writeSuccNum : 6
     * writeSuccRate : 0
     * selectNum : 3
     * selectSuccNum : 3
     * selectSuccRate : 0
     * jurisdicStatus : 6
     */

    private String id;
    private boolean isNewRecord;
    private String name;
    private String userId;
    private String photo;
    private int writeNum;
    private int writeSuccNum;
    private int writeSuccRate;
    private int selectNum;
    private int selectSuccNum;
    private int selectSuccRate;
    private int jurisdicStatus;

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public int getWriteSuccRate() {
        return writeSuccRate;
    }

    public void setWriteSuccRate(int writeSuccRate) {
        this.writeSuccRate = writeSuccRate;
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

    public int getSelectSuccRate() {
        return selectSuccRate;
    }

    public void setSelectSuccRate(int selectSuccRate) {
        this.selectSuccRate = selectSuccRate;
    }

    public int getJurisdicStatus() {
        return jurisdicStatus;
    }

    public void setJurisdicStatus(int jurisdicStatus) {
        this.jurisdicStatus = jurisdicStatus;
    }
}
