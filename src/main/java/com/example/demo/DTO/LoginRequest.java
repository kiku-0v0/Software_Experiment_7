package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
WebApi返回的login请求体
 */
public class LoginRequest {
    @JsonProperty("Username")
    private String Username;
    @JsonProperty("Password")
    private String Password;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String toString(){
        return getUsername();
    }

}
