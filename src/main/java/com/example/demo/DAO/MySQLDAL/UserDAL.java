package com.example.demo.DAO.MySQLDAL;


import com.example.demo.DAO.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
对表t_user进行操作
 */
public class UserDAL {
    public void Add(User user) throws SQLException {

        String url = "jdbc:mysql://localhost:3306/experiment_2";  // 数据库URL
        String username = "root";  // 数据库用户名
        String password = "123456";  // 数据库密码
        // 插入SQL语句，使用参数占位符 "?"
        String sql = "INSERT INTO t_user (fID, fOrgIDs, fUserGUID, fName, fPassword, fRemark) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        // 使用 JDBC 执行插入操作
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // 设置参数
            ps.setString(1,user.getfID());// 第一个参数，fID
            ps.setString(2, user.getfOrgIDs());//第二个参数
            ps.setString(3, user.getfUserGUID()); // 第三个参数
            ps.setString(4, user.getfName());// 第四个参数
            ps.setString(5, user.getfPassword());// 第五个参数
            ps.setString(6, user.getfRemark());// 第六个参数

            // 执行插入操作
            int rowsAffected = ps.executeUpdate();
            // 输出受影响的行数
            System.out.println(rowsAffected + " row(s) inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Update(User user){
        String url = "jdbc:mysql://localhost:3306/experiment_2";  // 数据库URL
        String username = "root";  // 数据库用户名
        String password = "123456";  // 数据库密码
        // 插入SQL语句，使用参数占位符 "?"
        String sql = "UPDATE t_user SET fID = ?,fOrgIDs = ?,fUserGUID = ?,fName = ?,fPassword = ?,fRemark = ? "
                + "where fName = ?" ;

        // 使用 JDBC 执行插入操作
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // 设置参数
            ps.setInt(1, Integer.parseInt(user.getfID()));// 第一个参数，fID
            ps.setString(2, user.getfOrgIDs());          // 第二个参数，fName
            ps.setString(3, user.getfUserGUID()); // 第三个参数，fHigherUpfIDs
            ps.setString(4, user.getfName());   // 第四个参数，fPermission
            ps.setString(5, user.getfPassword());       // 第五个参数，fRemark
            ps.setString(6, user.getfRemark());      // 第六个参数，fOrgGUID
            ps.setString(7, user.getfName());

            // 执行插入操作
            int rowsAffected = ps.executeUpdate();
            // 输出受影响的行数
            System.out.println(rowsAffected + " row(s) Update.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public  void Delete(User user) {
        String url = "jdbc:mysql://localhost:3306/experiment_2";  // 数据库URL
        String username = "root";  // 数据库用户名
        String password = "123456";  // 数据库密码
        // 删除SQL语句，使用参数占位符 "?"
        String sql = "DELETE from t_user where fName = ?" ;

        // 使用 JDBC 执行插入操作
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // 设置参数
            ps.setString(1,user.getfName());

            // 执行操作
            int rowsAffected = ps.executeUpdate();
            // 输出受影响的行数
            System.out.println(rowsAffected + " row(s) Update.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } //删除数据库一条数据

    public List<User> QueryModelList(String strWhere){
        List<User> resultList = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/experiment_2";  // 数据库URL
        String username = "root";  // 数据库用户名
        String password = "123456";  // 数据库密码
        // 删除SQL语句，使用参数占位符 "?"
        String sql = "Select * from t_user " + strWhere;

        // 使用 JDBC 执行插入操作
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while(rs.next()){
                String fID = rs.getString("fID");
                String fOrgIDs = rs.getString("fOrgIDs");
                String fUserGUID = rs.getString("fUserGUID");
                String fName = rs.getString("fName");
                String fPassword = rs.getString("fPassword");
                String fRemark = rs.getString("fRemark");

                User user = new User(fID,fOrgIDs,fUserGUID,fName,fPassword,fRemark);
                resultList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultList;
    }//根据查询条件返回机构列表

}