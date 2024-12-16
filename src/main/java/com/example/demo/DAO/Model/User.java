package com.example.demo.DAO.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
用户类，对应t_user表中的一条记录
 */
public class User {
    private String fID;
    private String fOrgIDs;
    private String fUserGUID;
    private String fName;
    private String fPassword;
    private String fRemark;

    public User(String fID, String fOrgIDs, String fUserGUID, String fName, String fPassword, String fRemark) {
        this.fID = fID;
        this.fOrgIDs = fOrgIDs;
        this.fUserGUID = fUserGUID;
        this.fName = fName;
        this.fPassword = fPassword;
        this.fRemark = fRemark;
    }

    @JsonIgnore
    public List<String> getParentIDs(){
        List<String> parentIDs = Arrays.stream(fOrgIDs.split("\\|"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());//用来解析fOrgIDs,去掉'|'字符
        return parentIDs;
    }
    public String getfID() {
        return fID;
    }

    public void setfID(String fID) {
        this.fID = fID;
    }

    public String getfOrgIDs() {
        return fOrgIDs;
    }

    public void setfOrgIDs(String fOrgIDs) {
        this.fOrgIDs = fOrgIDs;
    }

    public String getfUserGUID() {
        return fUserGUID;
    }

    public void setfUserGUID(String fUserGUID) {
        this.fUserGUID = fUserGUID;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getfPassword() {
        return fPassword;
    }

    public void setfPassword(String fPassword) {
        this.fPassword = fPassword;
    }

    public String getfRemark() {
        return fRemark;
    }

    public void setfRemark(String fRemark) {
        this.fRemark = fRemark;
    }

    @Override
    public String toString(){
        return "User{" +
                "fID='" + fID + '\'' +
                ", fOrgIDs='" + fOrgIDs + '\'' +
                ", fUserGUID='" + fUserGUID + '\'' +
                ", fName='" + fName + '\'' +
                ", fPassword='" + fPassword + '\'' +
                ", fRemark='" + fRemark + '\'' +
                '}';
    }

    public void updateUser(User user){
        setfID(user.getfID());
        setfName(user.getfName());
        setfRemark(user.getfRemark());
        setfPassword(user.getfPassword());
        setfUserGUID(user.getfUserGUID());
        setfOrgIDs(user.getfOrgIDs());
    }
}
