package com.example.backend.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "scene")
public class Scene {
    @Id
    @Column(name = "scene_id")
    private int scene_id;

    @Column(name="scene_name")
    private String scene_name;

    @Column(name="creator")
    private String creator;

    @Column(name="tags")
    private String tags;

    @Column(name="pwd")
    private String pwd;

    @Column(name="is_visible")
    private int is_visible;

    @Column(name="is_video")
    private int is_video;

    @Column(name="video_url")
    private String video_url;

    @Column(name="is_delete")
    private int is_delete;

    @Column(name="pic_url")
    private String pic_url;

    public Scene(int scene_id,String scene_name,String creator,String tags,String pwd,
                 int is_visible,int is_video,String video_url,int is_delete,String pic_url){
        this.scene_id=scene_id;
        this.scene_name=scene_name;
        this.creator=creator;
        this.tags=tags;
        this.pwd=pwd;
        this.is_visible=is_visible;
        this.is_video=is_video;
        this.video_url=video_url;
        this.is_delete=is_delete;
        this.pic_url=pic_url;
    }

    public Scene(){

    }

    public int getScene_id() {
        return scene_id;
    }

    public String getScene_name() {
        return scene_name;
    }

    public String getCreator() {
        return creator;
    }

    public String getTags() {
        return tags;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getIs_visible() {
        return is_visible;
    }

    public void setIs_visible(int is_visible) {
        this.is_visible = is_visible;
    }

    public int getIs_video() {
        return is_video;
    }

    public void setIs_video(int is_video) {
        this.is_video = is_video;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    public String getPic_url() {
        return pic_url;
    }

    public String getVideo_url() {
        return video_url;
    }
}
