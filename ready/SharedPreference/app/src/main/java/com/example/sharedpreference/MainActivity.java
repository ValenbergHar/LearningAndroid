package com.example.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.TextureView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);

     SharedPreferences.Editor editor=sharedPreferences.edit();
//        editor.putString("newHelloText", "Hello Shared Prferences");
        editor.clear();
      editor.apply();


        TextView textView = findViewById(R.id.textView);
        textView.setText(sharedPreferences.getString("newHelloText", "Default text"));


    }
}
