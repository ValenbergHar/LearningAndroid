package com.example.sqldemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String CUSTOMER_PERSON = "CUSTOMER_PERSON";
    public static final String COLUMN_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String COLUMN_CUSTOMER_AGE = "CUSTOMER_AGE";
    public static final String COLUMN_ACTIVE_CUSTOMER = "ACTIVE_CUSTOMER";
    public static final String COLUMN_ID = "ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "person.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + CUSTOMER_PERSON +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_CUSTOMER_NAME + " TEXT, " + COLUMN_CUSTOMER_AGE + " INT, " + COLUMN_ACTIVE_CUSTOMER + " BOOL)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CUSTOMER_NAME, person.getName());
        cv.put(COLUMN_CUSTOMER_AGE, person.getAge());
        cv.put(COLUMN_ACTIVE_CUSTOMER, person.isActive());
        long insert = db.insert(CUSTOMER_PERSON, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }

    }

    public List<Person> getEveryOne() {

        List<Person> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + CUSTOMER_PERSON;
        SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                int personID = cursor.getInt(0);
                String personName = cursor.getString(1);
                int personAge = cursor.getInt(2);
                boolean personActive = cursor.getInt(3) == 1 ? true : false;

                Person person = new Person(personID, personName, personAge, personActive);
                returnList.add(person);

            } while (cursor.moveToNext());
        } else {

        }

        cursor.close();
        db.close();

        return returnList;
    }
}
