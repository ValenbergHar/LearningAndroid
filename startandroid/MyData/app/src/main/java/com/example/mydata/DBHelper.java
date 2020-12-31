package com.example.mydata;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import static com.example.mydata.Util.LOG_TAG;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, Util.DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        String CREATE_CITY_TABLE =
                "create table " + Util.TABLE_NAME + " ("
                        + Util.KEY_ID + " integer primary key autoincrement, " + Util.KEY_NAME + " text,"
                        + Util.KEY_POPULATION + " integer" + ")";
        db.execSQL(CREATE_CITY_TABLE);


//        String CREATE_CITY_TABLE =
//                "create table " + Util.TABLE_NAME + " ("
//                        + Util.KEY_NAME + " text)";
//        db.execSQL(CREATE_CITY_TABLE);
//    }
//        String CREATE_DB = "create table " + Util.TABLE_NAME + " ( " + Util.KEY_NAME + " text);";
//        db.execSQL(CREATE_DB);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
