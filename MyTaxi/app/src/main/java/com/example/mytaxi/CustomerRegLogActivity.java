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

public class CustomerRegLogActivity extends AppCompatActivity {
    private TextView customer_status, customer_sign_up;
    private Button customer_btn_login, customer_btn_register;
    private TextInputEditText customer_email, customer_password;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_reg_log);

        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        customer_status=findViewById(R.id.customer_status);
        customer_sign_up=findViewById(R.id.customer_sign_up);
        customer_btn_login=findViewById(R.id.customer_btn_login);
        customer_btn_register=findViewById(R.id.customer_btn_register);
        customer_email=findViewById(R.id.customer_email);
        customer_password=findViewById(R.id.customer_password);

        customer_btn_register.setVisibility(View.INVISIBLE);
        customer_btn_register.setEnabled(false);


        customer_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customer_btn_register.setVisibility(View.VISIBLE);
                customer_btn_login.setVisibility(View.INVISIBLE);
                customer_sign_up.setVisibility(View.INVISIBLE);
                customer_btn_register.setEnabled(true);
                customer_status.setText(R.string.set_text_customer);
            }
        });
        customer_btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = customer_email.getText().toString();
                String password = customer_password.getText().toString();
                registerCustomer(mail, password);
            }
        });
    }

    private void registerCustomer(String mail, String password) {
        loadingBar.setTitle(getString(R.string.bar_register_customer));
        loadingBar.setMessage(getString(R.string.bar_wait));
        loadingBar.show();
        mAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CustomerRegLogActivity.this,
                            R.string.toast_ok,
                            Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                } else {
                    Toast.makeText(CustomerRegLogActivity.this,
                            toast_error,
                            Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }
        });
    }
}