package com.example.sharedpreferance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private TextView textView;
    private int counter = 0;
    private final static String KEY = "key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.txtView);

        pref = getSharedPreferences("TABLE", MODE_PRIVATE);
        counter = pref.getInt(KEY, 0);
        textView.setText(String.valueOf(counter));
    }

    public void onClick(View view) {
        counter++;
        textView.setText(String.valueOf(counter));
        saveData(counter);
    }

    public void saveData(int counter) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(KEY, counter);
        editor.apply();
    }
}