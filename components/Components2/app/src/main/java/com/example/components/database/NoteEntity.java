package com.example.components.database;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class NoteEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date date;
    private String text;


    public NoteEntity() {

    }

    @Ignore
    public NoteEntity(Date day, String text) {
        this.date = day;
        this.text = text;
    }

    public NoteEntity(int id, Date day, String text) {
        this.id = id;
        this.date = day;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
                ", day=" + date +
                ", text='" + text + '\'' +
                '}';
    }
}
