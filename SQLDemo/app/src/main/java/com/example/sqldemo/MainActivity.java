package com.example.sqldemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btn_view_all, btn_add;
    private EditText et_name, et_age;
    private Switch sw_active;
    private List<Person> person_list;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_view_all = findViewById(R.id.btn_view_all);
        btn_add = findViewById(R.id.btn_add);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        sw_active = findViewById(R.id.sw_active);

        recyclerView = findViewById(R.id.rv_customer_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecyclerViewAdapter(person_list, this);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Person person = new Person(-1, et_name.getText().toString(), Integer.valueOf(et_age.getText().toString()), sw_active.isChecked());
                    Toast.makeText(MainActivity.this, person.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "нека хуйня", Toast.LENGTH_SHORT).show();
                }
                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
            }
        });

        btn_view_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}