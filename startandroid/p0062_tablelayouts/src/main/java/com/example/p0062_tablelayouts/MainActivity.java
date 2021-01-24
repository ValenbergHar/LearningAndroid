package com.example.p0062_tablelayouts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textView;
    Button button;
    Button b2;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        textView = (TextView) findViewById(R.id.textView);


        button = (Button) findViewById(R.id.button);
        button.setText("Жыве Беларусь!");

        b2 = (Button) findViewById(R.id.b2);
        b2.setText("Жыве Вечна!");


        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setText("Жыве Беларусь!");


        button.setOnClickListener(this);
        b2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                textView.setText("націснута кнопачка Жыве Беларусь!");
                Toast.makeText(this, "Жыве!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.b2:
                textView.setText("націснута кнопачка Жыве Вечна!");
                Toast.makeText(this, "Вечна!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
