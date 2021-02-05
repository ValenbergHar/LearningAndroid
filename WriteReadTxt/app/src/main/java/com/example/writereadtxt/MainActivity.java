package com.example.writereadtxt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText enterText;
    private Button writeText, readText;
    private TextView showText;
    private String file = "txt.txt";
    private String fileContents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        writeText = findViewById(R.id.writeText);
        readText = findViewById(R.id.readText);
        enterText = findViewById(R.id.enterText);
        showText = findViewById(R.id.showText);

        writeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileContents = enterText.getText().toString();

                try {
                    FileOutputStream fileOutputStream = openFileOutput(file, MODE_PRIVATE);
                    fileOutputStream.write(fileContents.getBytes());
                    fileOutputStream.close();
                    File fileDir = new File(getFilesDir(), file);
                    Toast.makeText(MainActivity.this, "File saved at " + fileDir, Toast.LENGTH_SHORT).show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        readText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileInputStream fileInputStream = openFileInput(file);
                    int c;
                    String temp = " ";
                    while ((c = fileInputStream.read()) != -1) {
                        temp = temp + Character.toString((char) c);
                    }
                    showText.setText(temp);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}