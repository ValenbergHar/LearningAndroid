package com.example.p0341_simplesqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final String LOG_TAG = "myLogs";
    List<String> nameList;

    Button btnAdd;
    EditText etName;

    DBHelper dbHelper;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        btnAdd = findViewById(R.id.btnAddd);
        btnAdd.setOnClickListener(this);

        etName = findViewById(R.id.editTextName);
        nameList = new ArrayList<>();


        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);
    }


    @Override
    public void onClick(View v) {

        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // получаем данные из полей ввода
        String name = etName.getText().toString();


        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        switch (v.getId()) {
            case R.id.btnAddd:
                Log.d(LOG_TAG, "--- Insert in mytable: ---");
                // подготовим данные для вставки в виде пар: наименование столбца - значение

                cv.put("name", name);

                // вставляем запись и получаем ее ID
                long rowID = db.insert(Util.TABLE_NAME, null, cv);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                nameList.add(name);
                Log.d(LOG_TAG, "list inserted" + nameList.toString());
                break;

        }
        // закрываем подключение к БД
        dbHelper.close();
    }




}
