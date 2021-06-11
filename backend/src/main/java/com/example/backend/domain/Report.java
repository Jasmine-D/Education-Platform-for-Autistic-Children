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
@Table(name = "report")
public class Report {
    @Id
    @Column(name = "report_id")
    private int report_id;

    @Column(name="scene_id")
    private int scene_id;

    @Column(name="user_id")
    private int user_id;

    @Column(name="time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date time;

    @Column(name="score")
    private  int score;

    @Column(name = "comment")
    private String comment;

    @Column(name="teacher")
    private String teacher;

    public Report(){
        this.time=new Date();
    }

    public Report(int report_id,int scene_id,int user_id,Date time,int score,String comment,String teacher){
        this.report_id=report_id;
        this.scene_id=scene_id;
        this.user_id=user_id;
        this.time=time;
        this.score=score;
        this.comment=comment;
        this.teacher=teacher;
    }

    public int getReport_id() {
        return report_id;
    }

    public int getScene_id() {
        return scene_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
