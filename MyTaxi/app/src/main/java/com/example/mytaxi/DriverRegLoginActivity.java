package com.example.mytaxi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.mytaxi.R.string.toast_error;

public class DriverRegLoginActivity extends AppCompatActivity {
    private TextView driver_status, driver_sign_up;
    private Button driver_btn_login, driver_btn_register;
    private TextInputEditText driver_email, driver_password;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_reg_login);
        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);


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

        driver_btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = driver_email.getText().toString();
                String password = driver_password.getText().toString();
                registerDriver(mail, password);
            }
        });

    }

    private void registerDriver(String mail, String password) {
        loadingBar.setTitle(getString(R.string.bar_register_driver));
        loadingBar.setMessage(getString(R.string.bar_wait));
        loadingBar.show();
        mAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(DriverRegLoginActivity.this,
                            R.string.toast_ok,
                            Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                } else {
                    Toast.makeText(DriverRegLoginActivity.this,
                            toast_error,
                            Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }
        });

    }
}