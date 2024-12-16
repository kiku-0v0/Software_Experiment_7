package com.example.demo.DAO.MySQLDAL;

import com.example.demo.DAO.Model.Organization;
import com.example.demo.DAO.Model.User;

import java.sql.*;
import java.util.*;

/*
用于返回数据库的数据，返回成一个机构List，用户List
同时执行对数据库的操作，用于实现树形结构。
 */
public class DAL {
    //从数据库将数据映射到Organization类中，返回所有的机构。
    public static List<Organization> getAllOrganization() throws SQLException, ClassNotFoundException {
        List<Organization> organizations = new ArrayList<>();
        Map<String,Organization> orgMap = new HashMap<>();

        Class.forName("com.mysql.cj.jdbc.Driver");
        //从数据库查询所有机构信息
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/experiment_2", "root", "123456");
        Statement stmt = connection.createStatement();
        ResultSet orgResultSet = stmt.executeQuery("SELECT * FROM t_organization");

        //遍历ResultSet，就是每一行记录
        while (orgResultSet.next()) {
            String fID = orgResultSet.getString("fID");
            String fName = orgResultSet.getString("fName");
            String fPermission = orgResultSet.getString("fPermission");
            String fHigherUpIDs = orgResultSet.getString("fHigherUpfIDs");
            String fRemark = orgResultSet.getString("fRemark");
            String fOrgGUID = orgResultSet.getString("fOrgGUID");
            List<Organization> organizations1 = new ArrayList<>();
            List<User> users = getUsersByOrgID(fID);

            // 创建机构对象
            Organization org = new Organization(fID, fName, fPermission, fHigherUpIDs, fRemark, fOrgGUID,organizations1,users);

            if (fHigherUpIDs == null || fHigherUpIDs.equals("0")) {
                // 如果没有父机构，将其添加为根机构
                organizations.add(org); // 将根机构直接加入根机构列表
            } else {
                // 如果有父机构，查找父机构并添加
                Organization parentOrganization = orgMap.get(fHigherUpIDs);
                if (parentOrganization != null) {
                    parentOrganization.addOrg(org); // 将子机构放入父机构的childOrganization表中
                } else {
                    System.out.println("Parent organization with ID " + fHigherUpIDs + " not found!");
                }
            }

            // 将机构对象存入map
            orgMap.put(fID, org);

            if (fHigherUpIDs != null && !fHigherUpIDs.equals("0")) organizations.add(org);

        }

        orgResultSet.close();
        stmt.close();
        connection.close();

        return organizations;
    }

    //访问数据库得到所有的用户列表
    public static List<User> getAllUser() throws SQLException {
        List<User> users = new ArrayList<>();

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/experiment_2", "root", "123456");
        Statement stmt = connection.createStatement();
        ResultSet userResultSet = stmt.executeQuery("SELECT * FROM t_user");

        while(userResultSet.next()) {
            String fID = userResultSet.getString("fID");
            String fOrgIDs = userResultSet.getString("fOrgIDs");
            String fUserGUID = userResultSet.getString("fUserGUID");
            String fName = userResultSet.getString("fName");
            String fPassword = userResultSet.getString("fPassword");
            String fRemark = userResultSet.getString("fRemark");

            User user = new User(fID,fOrgIDs, fUserGUID, fName, fPassword, fRemark);

            users.add(user);
        }

        userResultSet.close();
        stmt.close();
        connection.close();

        return users;
    }

    //根据Orgid来查找用户
    public static List<User> getUsersByOrgID(String orgID) throws SQLException {
        List<User> users;
        users = getAllUser();

        List<User> result_users = new ArrayList<>();

        for(User u : users){
            for(String str : u.getParentIDs()){
                if(str.equals(orgID)){
                    result_users.add(u);
                    break;
                }
            }
        }//先遍历users表，再遍历u对象的fOrgIDs属性，如果与orgID相等，加入到result_users表中

        return result_users;
    }
}

