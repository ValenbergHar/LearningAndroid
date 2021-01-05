package com.example.awersomechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.awersomechat.UserListActivity.USER_NAME;

public class SignInActivity extends AppCompatActivity {


    private static final String TAG = "SignInActivity";
    private FirebaseAuth auth;
    private EditText editTextName, editTextPassword, editTextEmail, repeatEditTextPassword;
    private Button buttonSignUp;
    private TextView toggleLoginSignUpTextView;

    private boolean loginModeActive;

    private FirebaseDatabase dataBaseReference;
    private DatabaseReference usersDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);



        auth = FirebaseAuth.getInstance();
        editTextName = findViewById(R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        repeatEditTextPassword = findViewById(R.id.repeatEditTextPassword);
        toggleLoginSignUpTextView = findViewById(R.id.toggleLoginSignUpTextView);

        dataBaseReference = FirebaseDatabase.getInstance();
        usersDatabaseReference = dataBaseReference.getReference().child("users");

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginSignUpUser(editTextEmail.getText().toString().trim(), editTextPassword.getText().toString().trim());
            }
        });
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(SignInActivity.this, UserListActivity.class));
        }
    }

    private void loginSignUpUser(String email, String password) {
        if (loginModeActive) {
            if (editTextPassword.getText().toString().trim().length() < 7) {
                Toast.makeText(SignInActivity.this, "Password must be at least more 7 characters", Toast.LENGTH_SHORT).show();
            } else if (editTextEmail.getText().toString().trim().equals("")) {
                Toast.makeText(SignInActivity.this, "Please input your email", Toast.LENGTH_SHORT).show();
            } else {

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = auth.getCurrentUser();
                                    Intent intent = new Intent(SignInActivity.this, UserListActivity.class);
                                    intent.putExtra(USER_NAME, editTextName.getText().toString().trim());
                                    startActivity(intent);

//                                    startActivity(new Intent(SignInActivity.this, MainActivity.class));

//                                updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(SignInActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
//                                updateUI(null);

                                    // ...
                                }
                                // ...
                            }
                        });
            }
        } else if (!editTextPassword.getText().toString().trim().equals(repeatEditTextPassword.getText().toString().trim())) {
            Toast.makeText(SignInActivity.this, "Password don`t match", Toast.LENGTH_SHORT).show();
        } else if (editTextPassword.getText().toString().trim().length() < 7) {
            Toast.makeText(SignInActivity.this, "Password must be at least more 7 characters", Toast.LENGTH_SHORT).show();
        } else if (editTextEmail.getText().toString().trim().equals("")) {
            Toast.makeText(SignInActivity.this, "Please input your email", Toast.LENGTH_SHORT).show();
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = auth.getCurrentUser();
                                createUser(user);
                                Intent intent = new Intent(SignInActivity.this, UserListActivity.class);
                                intent.putExtra(USER_NAME, editTextName.getText().toString().trim());
                                startActivity(intent);
//                            updateUI(user);
                            } else {

                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                            }

                        }

                    });
        }
    }

    private void createUser(FirebaseUser firebaseUser) {
        User user= new User();
        user.setId(firebaseUser.getUid());
        user.setEmail(firebaseUser.getEmail());
        user.setName(editTextName.getText().toString().trim());
        usersDatabaseReference.push().setValue(user);
    }

    public void toggleLoginMode(View view) {
        if (loginModeActive) {
            loginModeActive = false;
            buttonSignUp.setText("Sign Up");
            toggleLoginSignUpTextView.setText("Or, log in");
            repeatEditTextPassword.setVisibility(View.VISIBLE);
        } else {
            loginModeActive = true;
            buttonSignUp.setText("Log In");
            toggleLoginSignUpTextView.setText("Or, log up");
            repeatEditTextPassword.setVisibility(View.GONE);
        }
    }

}