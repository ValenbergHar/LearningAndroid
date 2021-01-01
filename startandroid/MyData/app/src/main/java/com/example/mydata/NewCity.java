package com.example.mydata;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.mydata.Util.LOG_TAG;

public class NewCity extends AppCompatActivity implements View.OnClickListener {
    private Button btnAdd, btnDel;
    private DBHelper dbHelper;
    private EditText editTextId, editTextName, editTextPopulation;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_city_activity);
        dbHelper = new DBHelper(NewCity.this);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        editTextId=findViewById(R.id.editTextId);
        editTextName=findViewById(R.id.editTextName);
        editTextPopulation=findViewById(R.id.editTextPopulation);


    }

    @Override
    public void onClick(View v) {
        Toast.makeText(NewCity.this, "gghfyc", Toast.LENGTH_SHORT).show();
        Log.d(LOG_TAG, "--- Insert in mytable: ---");
        Intent intent = new Intent();
        intent.putExtra(Util.KEY_ID, Integer.valueOf(editTextId.getText().toString()));
        intent.putExtra(Util.KEY_NAME, (editTextName.getText().toString()));
        intent.putExtra(Util.KEY_POPULATION, Integer.valueOf(editTextPopulation.getText().toString()));


  //      dbHelper.addCity(new City(Integer.valueOf(editTextId.toString()), editTextName.toString(), Integer.valueOf(editTextPopulation.toString())));


        setResult(RESULT_OK, intent);
        finish();

    }
}
