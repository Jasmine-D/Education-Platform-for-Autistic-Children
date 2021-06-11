package com.example.backend.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "scene_pic")
public class ScenePic {
    @Id
    @Column(name = "pic_id")
    private int pic_id;

    @Column(name="scene_id")
    private int scene_id;

    @Column(name="pic_url")
    private String pic_url;

    @Column(name="keyword")
    private String keyword;

    @Column(name="description")
    private String description;

    public ScenePic(){

    }
    public ScenePic(int pic_id,int scene_id,String pic_url,String keyword,String description){
        this.pic_id=pic_id;
        this.scene_id=scene_id;
        this.pic_url=pic_url;
        this.keyword=keyword;
        this.description=description;
    }

    public int getPic_id() {
        return pic_id;
    }

    public int getScene_id() {
        return scene_id;
    }

    public String getPic_url() {
        return pic_url;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getDescription() {
        return description;
    }
}
