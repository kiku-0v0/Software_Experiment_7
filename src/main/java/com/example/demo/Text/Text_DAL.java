package com.example.demo.Text;


import com.example.demo.DAO.Model.Organization;
import com.example.demo.DAO.Model.User;
import com.example.demo.DAO.MySQLDAL.DAL;
import com.example.demo.DAO.MySQLDAL.OrganizationDAL;
import com.example.demo.DAO.MySQLDAL.UserDAL;
import com.example.demo.Service.TreeService;
import com.sun.source.tree.Tree;

import java.sql.SQLException;
import java.util.List;
/*
测试DAL的功能，strwhere是SQL语句中的条件语句
 */
public class Text_DAL {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //Organization organization = new Organization("2","1","1","1","1","1");
//        OrganizationDAL organizationDAL = new OrganizationDAL();
//        String strWhere = "WHERE fID LIKE '0101%'";
//        List<Organization> organizationList = organizationDAL.QueryModelList(strWhere);
//        for(Organization organization : organizationList){
//            System.out.println(organization);
//        }
        TreeService treeService = new TreeService();
        User user = treeService.getUserByfName("202225220608");
        System.out.println(user);

    }
}