package com.example.demo;

import com.example.demo.DAO.Model.User;
import com.example.demo.Service.TreeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.DTO.LoginRequest;

import java.sql.SQLException;
import java.util.List;

@RestController
public class userController {
    private final TreeService treeService = new TreeService();

    public userController() throws SQLException, ClassNotFoundException {
    }

    // 用户登录接口
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        boolean isSuccess = treeService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (isSuccess) {
            return ResponseEntity.ok("登录成功");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("登录失败");
        }
    }

    // 添加用户
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User user) throws SQLException {
        treeService.addUser(user);
        return ResponseEntity.ok("用户添加成功");
    }

    //根据用户ID查找
    @GetMapping("/user/{userID}")
    public ResponseEntity<?> getUser(@PathVariable String userID) {
        User user = treeService.getUserByID(userID);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("没有找到该用户");
        }
    }

    // 获取所有用户
    @GetMapping("/")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = treeService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // 更新用户
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        treeService.updateUser( user);
        return ResponseEntity.ok("用户属性更新成功");
    }

    // 删除用户
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@RequestBody User user) {
        treeService.deleteUser(user);
        return ResponseEntity.ok("用户删除成功");
    }

}
