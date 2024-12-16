package com.example.demo.DAO.MySQLDAL;

import com.example.demo.DAO.Model.Organization;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
对表t_organization进行操作
 */
public class OrganizationDAL {
    //添加数据库一条数据
    public void Add(Organization org) {

        String url = "jdbc:mysql://localhost:3306/experiment_2";  // 数据库URL
        String username = "root";  // 数据库用户名
        String password = "123456";  // 数据库密码
        // 插入SQL语句，使用参数占位符 "?"
        String sql = "INSERT INTO t_organization (fID, fName, fHigherUpfIDs, fPermission, fRemark, fOrgGUID) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        // 使用 JDBC 执行插入操作
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // 设置参数
            ps.setString(1, org.getfID());// 第一个参数，fID
            ps.setString(2, org.getfName());          // 第二个参数，fName
            ps.setString(3, org.getfHigherUpfIDs()); // 第三个参数，fHigherUpfIDs
            ps.setString(4, org.getfPermission());   // 第四个参数，fPermission
            ps.setString(5, org.getfRemark());       // 第五个参数，fRemark
            ps.setString(6, org.getfOrgGUID());      // 第六个参数，fOrgGUID

            // 执行插入操作
            int rowsAffected = ps.executeUpdate();
            // 输出受影响的行数
            System.out.println(rowsAffected + " row(s) inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //修改数据库一条数据
    public void Update(Organization org){
        String url = "jdbc:mysql://localhost:3306/experiment_2";  // 数据库URL
        String username = "root";  // 数据库用户名
        String password = "123456";  // 数据库密码
        // 插入SQL语句，使用参数占位符 "?"
        String sql = "UPDATE t_organization SET fID = ?,fName = ?,fHigherUpfIDs = ?,fPermission = ?,fRemark = ?,fOrgGUID = ? "
                + "where fName = ?" ;

        // 使用 JDBC 执行插入操作
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // 设置参数
            ps.setInt(1, Integer.parseInt(org.getfID()));// 第一个参数，fID
            ps.setString(2, org.getfName());          // 第二个参数，fName
            ps.setString(3, org.getfHigherUpfIDs()); // 第三个参数，fHigherUpfIDs
            ps.setString(4, org.getfPermission());   // 第四个参数，fPermission
            ps.setString(5, org.getfRemark());       // 第五个参数，fRemark
            ps.setString(6, org.getfOrgGUID());      // 第六个参数，fOrgGUID
            ps.setString(7, org.getfName());

            // 执行插入操作
            int rowsAffected = ps.executeUpdate();
            // 输出受影响的行数
            System.out.println(rowsAffected + " row(s) Update.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //删除数据库一条数据
    public  void Delete(Organization org) {
        String url = "jdbc:mysql://localhost:3306/experiment_2";  // 数据库URL
        String username = "root";  // 数据库用户名
        String password = "123456";  // 数据库密码
        // 删除SQL语句，使用参数占位符 "?"
        String sql = "DELETE from t_organization where fName = ?" ;


        // 使用 JDBC 执行插入操作
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // 设置参数
            ps.setString(1,org.getfName());

            // 执行操作
            int rowsAffected = ps.executeUpdate();
            // 输出受影响的行数
            System.out.println(rowsAffected + " row(s) Update.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //根据查询条件返回机构列表,strWhere字符串要带有where
    public List<Organization> QueryModelList(String strWhere){
        List<Organization> resultList = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/experiment_2";  // 数据库URL
        String username = "root";  // 数据库用户名
        String password = "123456";  // 数据库密码
        // 删除SQL语句，使用参数占位符 "?"
        String sql = "Select * from t_organization " + strWhere;

        // 使用 JDBC 执行插入操作
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while(rs.next()){
                String fID = rs.getString("fID");
                String fName = rs.getString("fName");
                String fPermission = rs.getString("fPermission");
                String fHigherUpIDs = rs.getString("fHigherUpfIDs");
                String fRemark = rs.getString("fRemark");
                String fOrgGUID = rs.getString("fOrgGUID");

                Organization org = new Organization(fID,fName,fPermission,fHigherUpIDs,fRemark,fOrgGUID);
                resultList.add(org);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultList;
    }

}