package com.example.whatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PrivacyActivity extends AppCompatActivity {
private Button back_policy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        back_policy = findViewById(R.id.back_policy);

        back_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent welcomeIntent = new Intent(PrivacyActivity.this, WelcomeActivity.class);
                startActivity(welcomeIntent);
            }
        });
    }
}