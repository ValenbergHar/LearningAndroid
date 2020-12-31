package com.example.p0341_simplesqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.p0341_simplesqlite.Util.LOG_TAG;

class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, Util.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        // создаем таблицу с полями

//            db.execSQL("create table mytable ("
//                    + "id integer primary key autoincrement,"
//                    + "name text,"
//                    + "email text" + ");");
//        }
        String CREATE_DB = "create table " + Util.TABLE_NAME + " ( " + Util.KEY_NAME + " text);";
        db.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}