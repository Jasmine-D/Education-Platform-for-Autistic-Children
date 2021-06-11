package com.example.backend.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "word_set")
public class Word_set {
    @Id
    @Column(name = "set_id")
    private int set_id;

    @Column(name = "set_name")
    private String set_name;

    @Column(name = "set_creator")
    private String set_creator;

    @Column(name = "introduction")
    private String introduction ;

    @Column(name = "is_visible")
    private int is_visible;

    @Column(name = "pwd")
    private String pwd;

    @Column(name = "card_num")
    private int card_num;

    public Word_set() {

    }

    public Word_set(String set_name, String set_creator, String introduction, int is_visible, String pwd){
        this.set_name = set_name;
        this.set_creator = set_creator;
        this.introduction = introduction;
        this.is_visible = is_visible;
        this.pwd = pwd;
        this.card_num = 0;
    }

    public int getSet_id() { return set_id; }

    public String getSet_name() { return set_name; }

    public String getSet_creator() { return set_creator; }

    public String getPwd() { return pwd; }

    public String getIntroduction() { return introduction; }

    public int getIs_visible() { return is_visible; }

    public int getCard_num() { return card_num; }

    public void setSet_id(int set_id) { this.set_id = set_id; }

    public void setSet_name(String set_name) { this.set_name = set_name; }

    public void setSet_creator(String set_creator) { this.set_creator = set_creator; }

    public void setIntroduction(String introduction) { this.introduction = introduction; }

    public void setIs_visible(int is_visible) { this.is_visible = is_visible; }

    public void setPwd(String pwd) { this.pwd = pwd; }

    public void setCard_num(int card_num) { this.card_num = card_num; }
}
