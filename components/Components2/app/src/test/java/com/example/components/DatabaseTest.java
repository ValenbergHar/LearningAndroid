package com.example.components;


import android.app.Instrumentation;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.example.components.database.AppDatabase;
import com.example.components.database.NoteDao;
import com.example.components.database.NoteEntity;
import com.example.components.utilities.SampleData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.junit.runners.JUnit4;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.core.app.ApplicationProvider;

import static org.junit.Assert.*;

//@RunWith(JUnit4.class)
////@RunWith(RobolectricTestRunner.class)
////@Config(sdk = Build.VERSION_CODES.P)
//public class DatabaseTest {
//    public static final String TAG = "Junit";
//    private AppDatabase mDb;
//    private NoteDao mDao;
//
//    @Before
//    public void createDb() {
////        Context context =  InstrumentationRegistry.getInstrumentation().getTargetContext();
////        Context context = ApplicationProvider.getApplicationContext();
//
//        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
//        mDao = mDb.noteDao();
//        Log.i(TAG, "createDb");
//    }
//
//    @After
//    public void closeDb() {
//        mDb.close();
//        Log.i(TAG, "closeDb");
//    }
//
//    @Test
//    public void createAndRetrieve() {
//        mDao.insertAll(SampleData.getNotes());
//        int count = mDao.getCount();
//        Log.i(TAG, "createAndRetrieve= " + count);
//        assertEquals(SampleData.getNotes().size(), count);
//    }
//}
