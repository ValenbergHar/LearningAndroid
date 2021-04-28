package com.example.mytaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class DriverRegLoginActivity extends AppCompatActivity {
    private TextView driver_status, driver_sign_up;
    private Button driver_btn_login, driver_btn_register;
    private TextInputEditText driver_email, driver_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_reg_login);

        driver_status = findViewById(R.id.driver_status);
        driver_sign_up = findViewById(R.id.driver_sign_up);
        driver_btn_login = findViewById(R.id.driver_btn_login);
        driver_btn_register = findViewById(R.id.driver_btn_register);
        driver_email = findViewById(R.id.driver_email);
        driver_password = findViewById(R.id.driver_password);

        driver_btn_register.setVisibility(View.INVISIBLE);
        driver_btn_register.setEnabled(false);

        driver_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                driver_btn_register.setVisibility(View.VISIBLE);
                driver_btn_login.setVisibility(View.INVISIBLE);
                driver_sign_up.setVisibility(View.INVISIBLE);
                driver_btn_register.setEnabled(true);
                driver_status.setText("Рэгістрацыя для кіроўцаў");
            }
        });

    }
}