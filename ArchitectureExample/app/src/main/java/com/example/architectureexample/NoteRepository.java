package com.example.architectureexample;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private  NoteDao noteDao;
    private LiveData<List<Note>> allnotes;

    public NoteRepository(Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);
    }

}
