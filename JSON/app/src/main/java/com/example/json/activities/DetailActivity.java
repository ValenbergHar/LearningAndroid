package com.example.json.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.json.R;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView title, pesterUrl, year, director, plot, runtime;



//    intent.putExtra(KEY_TITLE, clickedMovie.getTitle());
//        intent.putExtra(KEY_POSTER_URL, clickedMovie.getPosterUrl());
//        intent.putExtra(KEY_YEAR, clickedMovie.getYear());
//        intent.putExtra(KEY_DIRECTOR, clickedMovie.getDirector());
//        intent.putExtra(KEY_PLOT, clickedMovie.getPlot());
//        intent.putExtra(KEY_RUNTIME, clickedMovie.getRuntime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);



        Intent intent = getIntent();






    }
}