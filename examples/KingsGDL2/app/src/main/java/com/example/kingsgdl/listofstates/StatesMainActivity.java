package com.example.kingsgdl.listofstates;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import com.example.kingsgdl.R;
import com.example.kingsgdl.kings.MainActivityKings;
import com.google.android.material.appbar.MaterialToolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import drawer.MainActivityDrawer;

public class StatesMainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnPolackKniastva, btnVKL, btnRP, btnRusOcc, btnBNR, btnZahBiel, btnBSSRFirst, btnNacOcc, btnBSSRSecond, btnRB;
    private MaterialToolbar toolBarStates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_states);

        toolBarStates = findViewById(R.id.toolBarStates);
        setSupportActionBar(toolBarStates);

        btnPolackKniastva = findViewById(R.id.btnPolackKniastva);
        btnPolackKniastva.setOnClickListener(this);
        btnVKL = findViewById(R.id.btnVKL);
        btnVKL.setOnClickListener(this);
        btnRP = findViewById(R.id.btnRP);
        btnRP.setOnClickListener(this);
        btnRusOcc = findViewById(R.id.btnRusOcc);
        btnRusOcc.setOnClickListener(this);
        btnBNR = findViewById(R.id.btnBNR);
        btnBNR.setOnClickListener(this);
        btnZahBiel = findViewById(R.id.btnZahBiel);
        btnZahBiel.setOnClickListener(this);
        btnBSSRFirst = findViewById(R.id.btnBSSRFirst);
        btnBSSRFirst.setOnClickListener(this);
        btnNacOcc = findViewById(R.id.btnNacOcc);
        btnNacOcc.setOnClickListener(this);
        btnBSSRSecond = findViewById(R.id.btnBSSRSecond);
        btnBSSRSecond.setOnClickListener(this);
        btnRB = findViewById(R.id.btnRB);
        btnRB.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPolackKniastva:
                startActivity(new Intent(StatesMainActivity.this, MainActivityKings.class));

                break;
            case R.id.btnVKL:
                startActivity(new Intent(StatesMainActivity.this, MainActivityKings.class));
                break;
            case R.id.btnRP:
                startActivity(new Intent(StatesMainActivity.this, MainActivityKings.class));

                break;
            case R.id.btnRusOcc:
                startActivity(new Intent(StatesMainActivity.this, MainActivityKings.class));

                break;
            case R.id.btnBNR:
                startActivity(new Intent(StatesMainActivity.this, MainActivityKings.class));

                break;
            case R.id.btnZahBiel:
                startActivity(new Intent(StatesMainActivity.this, MainActivityKings.class));

                break;
            case R.id.btnBSSRFirst:
                startActivity(new Intent(StatesMainActivity.this, MainActivityKings.class));

                break;
            case R.id.btnNacOcc:
                startActivity(new Intent(StatesMainActivity.this, MainActivityKings.class));

                break;
            case R.id.btnBSSRSecond:
                startActivity(new Intent(StatesMainActivity.this, MainActivityKings.class));

                break;
            case R.id.btnRB:
                startActivity(new Intent(StatesMainActivity.this, MainActivityKings.class));

                break;
            default:
                break;
        }
    }
}
