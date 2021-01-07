package com.example.uibasics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button;
    private EditText editText;
    private TextView textView;
    private CheckBox checkBoxMatrix, checkBoxHarry, checkBoxHero;
    private RadioGroup radioGroup;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btnHello);
        button.setOnClickListener(this);

        editText = findViewById(R.id.editText);
        editText.setOnClickListener(this);

        textView = findViewById(R.id.txtHello);

        checkBoxMatrix = findViewById(R.id.checkboxMatrix);
        checkBoxHarry = findViewById(R.id.checkboxHarry);
        checkBoxHero = findViewById(R.id.checkboxHero);
        radioGroup = findViewById(R.id.relation);
        progressBar= findViewById(R.id.progressBar);



        checkBoxMatrix.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Toast.makeText(MainActivity.this, "Matrix", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(MainActivity.this, "No Matrix", Toast.LENGTH_SHORT).show();
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rbOne:
                        Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rbTwo:
                        Toast.makeText(MainActivity.this, "2", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rbThree:
                        Toast.makeText(MainActivity.this, "3", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnHello:
                Toast.makeText(this, "Bielarus", Toast.LENGTH_SHORT).show();
                textView.setText("Zdarou, " + editText.getText().toString());
                break;
            case R.id.editText:
                Toast.makeText(this, "Edit Text", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
    }
}