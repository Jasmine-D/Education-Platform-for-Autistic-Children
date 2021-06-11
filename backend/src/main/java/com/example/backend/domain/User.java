package com.example.backend.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @Column(name = "user_id")
    private int user_id;

    @Column(name="phone")
    private String phone;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="role")
    private int role;

    @Column(name="avatar")
    private String avatar;

    @Column(name="gender")
    private int gender;

    @Column(name="birthday")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    public User(){
        this.gender=0;
        this.birthday=new Date();
        this.avatar="avatar.png";
    }

    public int getUser_id() {//不可修改
        return user_id;
    }

    public String getPhone() {//不可修改
        return phone;
    }

    public String getUsername() {//不可修改
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {//可以修改
        this.password = password;
    }

    public int getRole() {//不可修改
        return role;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {//可以修改
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {//可以修改
        this.avatar = avatar;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {//可以修改
        this.birthday = birthday;
    }
}

