package com.example.kingsgdl.listofstates;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kingsgdl.R;
import com.example.kingsgdl.kings.MainActivityKings;
import com.example.kingsgdl.kings.tablelayout.Utils;
import com.google.android.material.appbar.MaterialToolbar;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class StatesMainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnPolackKniastva, btnVKL, btnRP, btnRusOcc, btnBNR, btnZahBiel, btnBSSRFirst, btnNacOcc, btnBSSRSecond, btnRB;
    private MaterialToolbar toolBarStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_states);

        toolBarStates = findViewById(R.id.toolBarStates);
        setSupportActionBar(toolBarStates);
        initBtn();

    }

    private void initBtn() {
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
        switch (v.getId()) {
            case R.id.btnPolackKniastva:
                Intent intentPolackKniastva = new Intent(StatesMainActivity.this, MainActivityKings.class);
                intentPolackKniastva.putExtra("intent", (Serializable) Utils.getKingsPK());
                startActivity(intentPolackKniastva);
                break;
            case R.id.btnVKL:
                Intent intentVKL = new Intent(StatesMainActivity.this, MainActivityKings.class);
                intentVKL.putExtra("intent", (Serializable) Utils.getKingsVkl());
                startActivity(intentVKL);
                break;
            case R.id.btnRP:
                Intent intentRP = new Intent(StatesMainActivity.this, MainActivityKings.class);
                intentRP.putExtra("intent", (Serializable) Utils.getKingsRP());
                startActivity(intentRP);
                break;
            case R.id.btnRusOcc:
                Intent intentRusOcc = new Intent(StatesMainActivity.this, MainActivityKings.class);
                intentRusOcc.putExtra("intent", (Serializable) Utils.getKingsRusAc());
                startActivity(intentRusOcc);
                break;
            case R.id.btnBNR:
                 Intent intentbtnBNR = new Intent(StatesMainActivity.this, MainActivityKings.class);
                intentbtnBNR.putExtra("intent", (Serializable) Utils.getKingsBNR());
                startActivity(intentbtnBNR);
                break;
            case R.id.btnZahBiel:
                Intent intentbtnZahBiel = new Intent(StatesMainActivity.this, MainActivityKings.class);
                intentbtnZahBiel.putExtra("intent", (Serializable) Utils.getKingsZahBiel());
                startActivity(intentbtnZahBiel);
                break;
            case R.id.btnBSSRFirst:
                Intent intentZahBiel = new Intent(StatesMainActivity.this, MainActivityKings.class);
                intentZahBiel.putExtra("intent", (Serializable) Utils.getKingsBSSRFirst());
                startActivity(intentZahBiel);
                break;
            case R.id.btnNacOcc:
                Intent intentNacOcc = new Intent(StatesMainActivity.this, MainActivityKings.class);
                intentNacOcc.putExtra("intent", (Serializable) Utils.getKingsNacOcc ());
                startActivity(intentNacOcc);
                break;
            case R.id.btnBSSRSecond:
                Intent intentBSSRSecond = new Intent(StatesMainActivity.this, MainActivityKings.class);
                intentBSSRSecond.putExtra("intent", (Serializable) Utils.getKingsBSSRSecond());
                startActivity(intentBSSRSecond);
                break;
            case R.id.btnRB:
                Intent intentRB = new Intent(StatesMainActivity.this, MainActivityKings.class);
                intentRB.putExtra("intent", (Serializable) Utils.getKingsRB());
                startActivity(intentRB);
                break;
            default:
                break;
        }
    }
}
