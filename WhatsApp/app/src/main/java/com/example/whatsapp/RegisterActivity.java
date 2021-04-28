package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.widget.Toast.*;

public class RegisterActivity extends AppCompatActivity {
    private Button register_btn, login_btn;
    private EditText email_input, password_input;
    private TextView changeTV;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register_btn = findViewById(R.id.register_btn);
        login_btn = findViewById(R.id.login_btn);
        email_input = findViewById(R.id.email_input);
        password_input = findViewById(R.id.password_input);
        changeTV = findViewById(R.id.changeTV);
        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        changeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTV.setVisibility(View.INVISIBLE);
                register_btn.setVisibility(View.INVISIBLE);
                login_btn.setVisibility(View.VISIBLE);
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInAccount();
            }
        });
    }

    private void signInAccount() {
        String email = email_input.getText().toString();
        String password = password_input.getText().toString();
        if (TextUtils.isEmpty(email)) {
            makeText(this, R.string.toast_email, LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)) {
            makeText(this, R.string.toast_password, LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle(getString(R.string.toast_entrance));
            loadingBar.setMessage(getString(R.string.toast_please_wait));
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);

                        loadingBar.dismiss();
                        makeText(RegisterActivity.this, R.string.toast_ok_login, LENGTH_SHORT).show();
                        finish();
                    } else {
                        String message = task.getException().toString();
                        makeText(RegisterActivity.this, message, LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private void createAccount() {
        String email = email_input.getText().toString();
        String password = password_input.getText().toString();
        if (TextUtils.isEmpty(email)) {
            makeText(this, R.string.toast_email, LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)) {
            makeText(this, R.string.toast_password, LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle(R.string.toast_creating_account);
            loadingBar.setMessage(getString(R.string.toast_please_wait));
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);

                        loadingBar.dismiss();
                        makeText(RegisterActivity.this, R.string.toast_ok, LENGTH_SHORT).show();
                        finish();
                    } else {
                        String message = task.getException().toString();
                        makeText(RegisterActivity.this, message, LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}