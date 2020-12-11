package com.bawp.sprefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private static final String MESSAGE_ID = "messages_prefs" ;
    private Button saveButton;
    private EditText enterMessage;
    private TextView showMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveButton = findViewById(R.id.button);
        enterMessage = findViewById(R.id.message_editText);
        showMessageTextView = findViewById(R.id.show_message_textview);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = enterMessage.getText().toString();

                SharedPreferences sharedPreferences = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("message", message);
                editor.apply();

            }
        });

//Get stuff back
        SharedPreferences getSharedPrefs = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);
        String value = getSharedPrefs.getString("message", "nothing");


        showMessageTextView.setText(value);

    }
}
