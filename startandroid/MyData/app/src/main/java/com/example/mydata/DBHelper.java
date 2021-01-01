package com.example.mydata;

import android.content.ContentValues;
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
    private DBHelper dbHelper;



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


    public void addCity(City city){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_ID, city.getId());
        contentValues.put(Util.KEY_NAME,city.getName());
        contentValues.put(Util.KEY_POPULATION,city.getPopulation());
        db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
