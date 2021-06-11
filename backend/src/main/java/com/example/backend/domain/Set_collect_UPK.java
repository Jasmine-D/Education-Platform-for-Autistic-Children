package com.example.backend.domain;

import java.io.Serializable;

public class Set_collect_UPK implements Serializable {
    private int user_id;
    private int set_id;

    public int getUser_id() { return user_id; }

    public int getSet_id() { return set_id; }

    public void setSet_id(int set_id) { this.set_id = set_id; }

    public void setUser_id(int user_id) { this.user_id = user_id; }
}
