package com.example.components.model;

import java.util.Date;

public class NoteEntity {
    private int id;
    private Date day;
    private String text;

    public NoteEntity() {

    }

    public NoteEntity(Date day, String text) {
        this.day = day;
        this.text = text;
    }

    public NoteEntity(int id, Date day, String text) {
        this.id = id;
        this.day = day;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "NoteEntity{" +
                "id=" + id +
                ", day=" + day +
                ", text='" + text + '\'' +
                '}';
    }
}
