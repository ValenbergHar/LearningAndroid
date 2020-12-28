package com.example.components.database;

import android.content.Context;
import android.graphics.drawable.Icon;

import androidx.lifecycle.LiveData;

import com.example.components.utilities.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    private static AppRepository ourInstance;

    public LiveData<List<NoteEntity>> mNotes;
    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context) {
        mNotes = getAllNotes();
        mDb = AppDatabase.getInstance(context);
    }

    public void addSampleData() {
        executor.execute(() -> mDb.noteDao().insertAll(SampleData.getNotes()));
    }
    private LiveData<List<NoteEntity>> getAllNotes(){
        return mDb.noteDao().getAll();
    }
}

