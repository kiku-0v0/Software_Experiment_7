package com.example.demo.DAO.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
表记录的具体实现类,机构类，对应表t_organization中的一条记录
 */
public class Organization{
    private String fID;
    private String fName;
    private String fPermission;//权限字段
    private String fHigherUpfIDs;//上级机构
    private String fRemark;
    private String fOrgGUID;

    private List<Organization> ChildOrganization = new ArrayList<>();//存放机构的下属机构,用来实现树形结构

    private List<User> ChildUser = new ArrayList<>();//存放该机构的下属成员

    public Organization() {
    }

    public Organization(String fID, String fName, String fPermission, String fHigherUpfIDs, String fRemark, String fOrgGUID) {
        this.fID = fID;
        this.fName = fName;
        this.fPermission = fPermission;
        this.fHigherUpfIDs = fHigherUpfIDs;
        this.fRemark = fRemark;
        this.fOrgGUID = fOrgGUID;
    }

    public Organization(String fID, String fName, String fPermission, String fHigherUpfIDs, String fRemark, String fOrgGUID, List<Organization> childOrganization, List<User> childUser) {
        this.fID = fID;
        this.fName = fName;
        this.fPermission = fPermission;
        this.fHigherUpfIDs = fHigherUpfIDs;
        this.fRemark = fRemark;
        this.fOrgGUID = fOrgGUID;
        ChildOrganization = childOrganization;
        ChildUser = childUser;
    }

    public String getfID() {
        return fID;
    }

    public void setfID(String fID) {
        this.fID = fID;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getfPermission() {
        return fPermission;
    }

    public void setfPermission(String fPermission) {
        this.fPermission = fPermission;
    }

    public String getfHigherUpfIDs() {
        return fHigherUpfIDs;
    }

    public void setfHigherUpfIDs(String fHigherUpfIDs) {
        this.fHigherUpfIDs = fHigherUpfIDs;
    }

    public String getfRemark() {
        return fRemark;
    }

    public void setfRemark(String fRemark) {
        this.fRemark = fRemark;
    }

    public String getfOrgGUID() {
        return fOrgGUID;
    }

    public void setfOrgGUID(String fOrgGUID) {
        this.fOrgGUID = fOrgGUID;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "fID='" + fID + '\'' +
                ", fName='" + fName + '\'' +
                ", fHigherUpIDs='" + fHigherUpfIDs + '\'' +
                ", fPermission='" + fPermission + '\'' +
                ", fRemark='" + fRemark + '\'' +
                ", fOrgGUID='" + fOrgGUID + '\'' +
                '}';
    }

    public void addOrg(Organization org) {
        ChildOrganization.add(org);
    }

    public void deleteOrg(Organization org){
        ChildOrganization.remove(org);
    }

    public boolean hasChildOrganization(Organization organization) {
        // 检查当前组织是否包含该子组织
        return ChildOrganization.contains(organization);  // 假设 childOrganizations 是存储子组织的列表
    }

    public void updateOrg(Organization org){
        setfID(org.getfID());
        setfName(org.getfName());
        setfRemark(org.getfRemark());
        setfPermission(org.getfPermission());
        setfOrgGUID(org.getfOrgGUID());
        setfHigherUpfIDs(org.getfHigherUpfIDs());
    }

    public void addUser(User user) {
        ChildUser.add(user);
    }

    public void deleteUser(User user){
        ChildUser.remove(user);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Organization that = (Organization) obj;
        return Objects.equals(fID, that.fID);  // 确保 fID 完全匹配
    }

    @Override
    public int hashCode() {
        return Objects.hash(fID);  // 确保 hashCode 根据 fID 计算
    }

}
