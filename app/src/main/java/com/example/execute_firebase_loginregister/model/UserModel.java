package com.example.execute_firebase_loginregister.model;

import java.io.Serializable;

public class UserModel implements Serializable {
    String uId;
    String name;
    String email;
    String password;
    String phone;
    String image;

    public UserModel(String uId, String name, String email, String password, String phone, String image) {
        this.uId = uId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.image = image;
    }

    public UserModel(String uId, String name, String email, String password, String phone) {
        this.uId = uId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public UserModel() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
