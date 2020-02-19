package com.example.assignmenttemplateproject.model;

public class User {
    private String userName;
    private String userPass;
    private String phone;
    private String fullName;

    public User(String userName, String userPass, String phone, String fullName) {
        this.userName = userName;
        this.userPass = userPass;
        this.phone = phone;
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
