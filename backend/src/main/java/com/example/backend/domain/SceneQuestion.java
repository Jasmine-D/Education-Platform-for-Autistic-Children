package com.example.backend.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "question")
public class SceneQuestion {
    @Id
    @Column(name = "question_id")
    private int question_id;

    @Column(name="scene_id")
    private int scene_id;

    @Column(name="content")
    private String content;

    @Column(name="pic_url")
    private String pic_url;

    public SceneQuestion(){

    }

    public SceneQuestion(int question_id, int scene_id, String content, String pic_url){
        this.question_id=question_id;
        this.scene_id=scene_id;
        this.content=content;
        this.pic_url=pic_url;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public int getScene_id() {
        return scene_id;
    }

    public String getContent() {
        return content;
    }

    public String getPic_url() {
        return pic_url;
    }
}
