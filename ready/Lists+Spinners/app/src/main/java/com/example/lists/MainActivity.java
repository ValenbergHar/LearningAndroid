package com.example.lists;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView citiesList;
    private Spinner spinnerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        citiesList = findViewById(R.id.citiesList);
        spinnerList = findViewById(R.id.spinner);

        final List<String> citiesSpin = Arrays.asList("fnfghnj", "Bierascie", "Miory", "Miensk", "Harodnia", "Slucak", "Bierascie", "Miory", "Miensk", "Harodnia", "Slucak", "Bierascie", "Miory", "Miensk", "Harodnia", "Slucak", "Bierascie", "Miory",
                "Miensk", "Harodnia", "Slucak", "Bierascie", "Miory", "Miensk", "Harodnia", "Slucak");

        final List<String> cities = Arrays.asList("Miensk", "Harodnia", "Slucak", "Bierascie", "Miory", "Miensk", "Harodnia", "Slucak", "Bierascie", "Miory", "Miensk", "Harodnia", "Slucak", "Bierascie", "Miory",
                "Miensk", "Harodnia", "Slucak", "Bierascie", "Miory", "Miensk", "Harodnia", "Slucak", "Bierascie", "Miory", "Miensk", "Harodnia", "Slucak", "Bierascie", "Miory", "Miensk", "Harodnia", "Slucak", "Bierascie", "Miory",
                "Miensk", "Harodnia", "Slucak", "Bierascie", "Miory", "Miensk", "Harodnia", "Slucak", "Bierascie", "Miory", "Miensk", "Harodnia", "Slucak", "Bierascie", "Miory", "Miensk", "Harodnia", "Slucak", "Bierascie", "Miory", "Miensk", "Harodnia", "Slucak", "Bierascie", "Miory");

        ArrayAdapter<String> citiesSpAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                citiesSpin
        );
        spinnerList.setAdapter(citiesSpAdapter);
        spinnerList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, citiesSpin.get(i) + " Selectied", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        ArrayAdapter<String> citiesAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                cities
        );

        citiesList.setAdapter(citiesAdapter);
        citiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, cities.get(i) + " Selectied", Toast.LENGTH_SHORT).show();
            }
        });

    }
}