package com.example.notes;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface NotesDao {
    @Query("SELECT * FROM notes ORDER BY dayOfWeek")
    LiveData<List<Note>> getAllNotes();

    @Insert
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("DELETE FROM notes")
    void deleteAllNotes();
}
