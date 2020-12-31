package com.example.mydata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.mydata.Util.KEY_ID;
import static com.example.mydata.Util.KEY_NAME;
import static com.example.mydata.Util.KEY_POPULATION;
import static com.example.mydata.Util.LOG_TAG;
import static com.example.mydata.Util.TABLE_NAME;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnAdd, btnDel;
    private EditText textId, textName, textPopulation;
    private List<City> cities;

    private DBHelper dbHelper;

    private RecyclerView recyclerView;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    long rowID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        btnDel = findViewById(R.id.btnDelete);
        btnDel.setOnClickListener(this);
        textId = findViewById(R.id.editTextId);
        textName = findViewById(R.id.editTextName);
        textPopulation = findViewById(R.id.editTextPopulation);

        cities = new ArrayList<>();

        cities.add(new City(1, "Hrodna", 350000));

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecyclerAdapter(cities, MainActivity.this);
        recyclerView.setAdapter(mAdapter);

        dbHelper = new DBHelper(this);

    }

    @Override
    public void onClick(View v) {
        // создаем объект для данных
        ContentValues cv = new ContentValues();
        // получаем данные из полей ввода
        int id = Integer.valueOf(textId.getText().toString());
        int population = Integer.valueOf(textPopulation.getText().toString());
        String name = textName.getText().toString();
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (v.getId()) {
            case R.id.btnAdd:
                Toast.makeText(MainActivity.this, "gghfyc", Toast.LENGTH_SHORT).show();
                Log.d(LOG_TAG, "--- Insert in mytable: ---");
                cv.put(KEY_ID, id);
                cv.put(KEY_NAME, name);
                cv.put(KEY_POPULATION, population);

                rowID = db.insert(TABLE_NAME, null, cv);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                cities.add(new City(id, name, population));
                Log.d(LOG_TAG, "cities " + cities.toString());
                break;
            default:
                break;
        }
        db.close();
    }
}