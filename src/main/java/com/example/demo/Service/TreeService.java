package com.example.demo.Service;


import com.example.demo.DAO.Model.Organization;
import com.example.demo.DAO.Model.User;
import com.example.demo.DAO.MySQLDAL.DAL;
import com.example.demo.DAO.MySQLDAL.OrganizationDAL;
import com.example.demo.DAO.MySQLDAL.UserDAL;

import java.sql.SQLException;
import java.util.List;

/*
实现树形权限结构封装：含数据加载（从数据持久层或 Manager 层）、
树形结构构建、节点的增加/删除/修改/查询（提供对树形结构的 CRUD）；
 */
public class TreeService {
    private final List<Organization> organizationList;
    private final List<User> UserList;
    private final OrganizationDAL organizationDAL = new OrganizationDAL();
    private final UserDAL userDAL = new UserDAL();

    public TreeService() throws SQLException, ClassNotFoundException {
        this.organizationList = DAL.getAllOrganization();
        this.UserList = DAL.getAllUser();
    }

    public boolean login(String userName,String password) {
        User user = getUserByfName(userName);
        return user != null && user.getfPassword().equals(password);
    }

    public Organization getOrganizationByID(String orgID){
        for(Organization org : organizationList){
            if(org.getfID().equals(orgID)){
                return org;
            }
        }
        System.out.println("该节点不存在");
        return null;
    }//根据机构ID查找机构

    public void addOrganization(Organization newOrganization){
        Organization parentOrg = getOrganizationByID(newOrganization.getfHigherUpfIDs());
        for(Organization org : organizationList){
            if(org.getfID().equals(newOrganization.getfID())){
                System.out.println("该机构已经存在");
                return;
            }
        }
        if(parentOrg != null){
            parentOrg.addOrg(newOrganization);
        }
        organizationList.add(newOrganization);
        organizationDAL.Add(newOrganization); // 同步修改数据库内容
        System.out.println("机构" + newOrganization.getfID() + "已添加成功");
    }//先找到新节点的父节点添加到他的子树中，再添加到存放所有机构的列表中。

    public void deleteOrganization(Organization organization){
        boolean found = false;

        for(Organization org : organizationList){
            if(org.getfID().equals(organization.getfID())){
                found = true;
                break;
            }
        }

        if(found) {
            // 先删除父组织中的子组织
            for (Organization parentOrg : organizationList) {
                if (parentOrg.hasChildOrganization(organization)) {
                    parentOrg.deleteOrg(organization);  // 假设 deleteOrg 方法删除子组织
                }
            }
            // 然后删除总组织列表中的该组织
            organizationList.removeIf(org -> org.getfID().equals(organization.getfID()));
            //对数据库进行处理
            organizationDAL.Delete(organization);
            System.out.println(organization.getfID() + "节点删除成功");
        }
        else{
            System.out.println("该节点不存在");
        }
    }//删除节点

    public void UpdateOrganization(Organization organization){
        Organization organization1 = getOrganizationByID(organization.getfID());
        if(!organization1.getfHigherUpfIDs().equals(organization.getfHigherUpfIDs())){
            for(Organization parentOrg : organizationList){
                if(parentOrg.getfID().equals(organization1.getfHigherUpfIDs())){
                    parentOrg.deleteOrg(organization1);
                    break;
                }
            }//从原来的父节点的子历表中删除

            for (Organization newparentOrg : organizationList){
                if(newparentOrg.getfID().equals(organization.getfHigherUpfIDs())){
                    newparentOrg.addOrg(organization1);
                }
            }//添加到新的父节点

            System.out.println("修改了上级机构");
        }//如果修改了机构的上级机构，对树结构进行修改

        organization1.updateOrg(organization);
        organizationDAL.Update(organization1);

        System.out.println(organization1.getfID() + "修改成功");

    }//修改节点

    public User getUserByID(String UserID){
        for(User user : UserList){
            if(user.getfID().equals(UserID)){
                return user;
            }
        }
        System.out.println("该用户不存在");
        return null;
    }//根据用户ID查找用户
    public User getUserByfName(String UserfName){
        for(User user : UserList){
            if(user.getfName().equals(UserfName)){
                return user;
            }
        }
        System.out.println("该用户不存在");
        return null;
    }

    public void addUser(User newUser) throws SQLException {
        boolean found = false;

        for(User user : UserList){
            if(user.getfID().equals(newUser.getfID())){
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("该用户已存在");
            return ;
        }//用户存在时跳过

        for (String parentID : newUser.getParentIDs()) {
            for(Organization parentOrg : organizationList){
                if(parentOrg.getfID().equals(parentID)){
                    parentOrg.addUser(newUser);
                }
            }
        }//加入父节点

        UserList.add(newUser);
        userDAL.Add(newUser);

    }//添加新User节点

    public void deleteUser(User user) {
        boolean found = false;

        for(User user1 : UserList){
            if(user1.getfID().equals(user.getfID())){
                found = true;
                break;
            }
        }

        if (found) {
            for(String parentID : user.getParentIDs()){
                for (Organization parentOrg :  organizationList){
                    if(parentOrg.getfID().equals(parentID)){
                        parentOrg.deleteUser(user);
                        break;
                    }
                }
            }//从机构的用户表中删除

            UserList.removeIf(user1 -> user1.getfID().equals(user.getfID()));//从总用户表中删除
            userDAL.Delete(user);

            System.out.println(user.getfID() + "删除成功");
        }
        else{
            System.out.println("该用户不存在");
        }

    }//删除用户

    public void updateUser(User user){
        User user1 = getUserByID(user.getfID());
        if(!user1.getfOrgIDs().equals(user.getfOrgIDs())){
            for(String parentID : user1.getParentIDs()){
                for(Organization parentOrg : organizationList){
                    if(parentOrg.getfID().equals(parentID)){
                        parentOrg.deleteUser(user1);
                        break;
                    }
                }
            }//从旧的机构里删除

            for (String newparentID : user.getParentIDs()){
                for (Organization newparentOrg : organizationList) {
                    if (newparentOrg.getfID().equals(newparentID)) {
                        newparentOrg.addUser(user);
                        break;
                    }
                }
            }//添加到新的机构里

            System.out.println("修改了上级机构");
        }//如果更改了用户的所属机构，进行修改
        user1.updateUser(user);
        userDAL.Update(user1);
        System.out.println(user1.getfID() + "修改成功");

    }//修改用户属性

    public List<User> getAllUsers() {
        return UserList;
    }
}
