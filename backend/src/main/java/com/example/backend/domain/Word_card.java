package com.example.backend.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "word_card")
public class Word_card {
    @Id
    @Column(name = "card_id")
    private int card_id;

    @Column(name = "set_id")
    private int set_id;

    @Column(name = "card_name")
    private String card_name;

    @Column(name = "card_content")
    private String card_content;

    @Column(name = "audio_url")
    private String audio_url;

    @Column(name = "pic_url")
    private String pic_url;

    public Word_card(){

    }

    public Word_card(int set_id, String card_name, String card_content, String audio_url, String pic_url){
        this.set_id=set_id;
        this.card_name=card_name;
        this.card_content=card_content;
        this.audio_url=audio_url;
        this.pic_url=pic_url;
    }

    public void setSet_id(int set_id) { this.set_id = set_id; }

    public void setAudio_url(String audio_url) { this.audio_url = audio_url; }

    public void setCard_content(String card_content) { this.card_content = card_content; }

    public void setCard_id(int card_id) { this.card_id = card_id; }

    public void setCard_name(String card_name) { this.card_name = card_name; }

    public void setPic_url(String pic_url) { this.pic_url = pic_url; }

    public int getSet_id() { return set_id; }

    public String getAudio_url() { return audio_url; }

    public String getCard_content() { return card_content; }

    public int getCard_id() { return card_id; }

    public String getCard_name() { return card_name; }

    public String getPic_url() { return pic_url; }
}
