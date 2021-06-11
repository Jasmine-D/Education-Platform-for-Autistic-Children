package com.example.backend.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "set_collect")
@IdClass(Set_collect_UPK.class)
public class Set_collect {
    @Id
    @Column(name = "user_id")
    private int user_id;

    @Id
    @Column(name = "set_id")
    private int set_id;

    public Set_collect(){

    }

    public Set_collect(int user_id, int set_id){
        this.user_id=user_id;
        this.set_id=set_id;
    }

    public void setSet_id(int set_id) { this.set_id = set_id; }

    public void setUser_id(int user_id) { this.user_id = user_id; }

    public int getUser_id() { return user_id; }

    public int getSet_id() { return set_id; }

}



