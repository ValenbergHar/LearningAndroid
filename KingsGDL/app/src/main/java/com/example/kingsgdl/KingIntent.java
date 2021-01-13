package com.example.kingsgdl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class KingIntent extends AppCompatActivity {
    private TextView name_king_item;
    private TextView date_king_item;
    private TextView story_king_item;
    private ImageView imageView_king_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_king_intent);
        name_king_item = findViewById(R.id.name_king_item);
        date_king_item = findViewById(R.id.date_king_item);
        imageView_king_item = findViewById(R.id.imageView_king_item);
        story_king_item = findViewById(R.id.story_king_item);


        Intent intent = getIntent();
        int id = Integer.valueOf(intent.getStringExtra("id"));
        String name = intent.getStringExtra("name");
        String date = intent.getStringExtra("date");
        String image = intent.getStringExtra("image");
        name_king_item.setText(name);
        date_king_item.setText(date);
        String[] strings = getResources().getStringArray((R.array.kings));

        for (int i = 0; i < strings.length; i++) {
            if (id == i) story_king_item.setText(strings[i]);
        }


        Glide.with(KingIntent.this).load(image).into(imageView_king_item);
    }
}