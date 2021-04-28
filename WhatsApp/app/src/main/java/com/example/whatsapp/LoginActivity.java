package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import static com.example.whatsapp.R.string.exception;
import static com.example.whatsapp.R.string.material_clock_display_divider;
import static com.example.whatsapp.R.string.sent_code;
import static com.example.whatsapp.R.string.toast_mistake;
import static com.example.whatsapp.R.string.toast_number;
import static com.example.whatsapp.R.string.toast_where_is_code;

public class LoginActivity extends AppCompatActivity {
    private Button next_btn, confirm_btn, register_email_btn;
    private EditText login_phone_input, login_verification_input;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        next_btn = findViewById(R.id.next_btn);
        confirm_btn = findViewById(R.id.confirm_btn);
        register_email_btn = findViewById(R.id.register_email_btn);
        login_phone_input = findViewById(R.id.login_phone_input);
        login_verification_input = findViewById(R.id.login_verification_input);
        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phoneNumber = login_phone_input.getText().toString();
                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(LoginActivity.this, toast_number, Toast.LENGTH_SHORT).show();
                } else {
                    startPhoneNumberVerification(phoneNumber);
                }
            }
        });

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String confirmCode = login_verification_input.getText().toString();
                if (TextUtils.isEmpty(confirmCode)) {
                    Toast.makeText(LoginActivity.this, toast_where_is_code, Toast.LENGTH_SHORT).show();
                } else {
                    verifyPhoneNumberWithCode(mVerificationId, confirmCode);
                }
            }
        });

        register_email_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                loadingBar.dismiss();
                Toast.makeText(LoginActivity.this, exception, Toast.LENGTH_SHORT).show();
                next_btn.setVisibility(View.VISIBLE);
                confirm_btn.setVisibility(View.INVISIBLE);
                login_phone_input.setVisibility(View.VISIBLE);
                login_verification_input.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                mVerificationId = verificationId;
                mResendToken = token;
                loadingBar.dismiss();
                Toast.makeText(LoginActivity.this, sent_code, Toast.LENGTH_SHORT).show();
                next_btn.setVisibility(View.INVISIBLE);
                confirm_btn.setVisibility(View.VISIBLE);
                login_phone_input.setVisibility(View.INVISIBLE);
                login_verification_input.setVisibility(View.VISIBLE);

            }
        };
    }


    private void startPhoneNumberVerification(String phoneNumber) {
        loadingBar.setTitle(getString(R.string.toast_checking_number));
        loadingBar.setMessage(getString(R.string.toast_please_wait));
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(LoginActivity.this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyPhoneNumberWithCode(String mVerificationId, String confirmCode) {
        loadingBar.setTitle(getString(R.string.toast_code_cheking));
        loadingBar.setMessage(getString(R.string.toast_please_wait));
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, confirmCode);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, R.string.toast_ok, Toast.LENGTH_SHORT).show();
                            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, toast_mistake, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}