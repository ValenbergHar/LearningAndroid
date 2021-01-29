package com.example.glide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;
    private String mImageAddress = "https://upload.wikimedia.org/wikipedia/commons/1/17/Пагоня%2C_Статут_1588.png";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.imageView);
        Glide.with(this).load(mImageAddress).into(mImageView);
    }
}