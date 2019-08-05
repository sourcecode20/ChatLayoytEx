package com.example.chatlayoytex.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.chatlayoytex.R;
import com.example.chatlayoytex.utils.Loader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    Button loginBtn;
    TextView signup;
    EditText editTextEmail, editTextPassword;
    Toolbar toolbar;
    Loader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        login_listener();
        signup_listener();
    }

    private void signup_listener() {
        signup = (TextView) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }

    private void login_listener() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            loader.show();

                editTextEmail = findViewById(R.id.editTextEmail);
                editTextPassword = findViewById(R.id.editTextPassword);

                String email, password;

                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();


                if (validation(email, password)) {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, NavigationActivity.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    loader.dismiss();
                }
            }
        });

    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        loader= new Loader(this);
    }

    private boolean validation(String email, String password) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Enter Email Address");
            return false;
        } else if (!email.matches(emailPattern)) {
            editTextEmail.setError("Enter valid Email Address");
            return false;
        } else if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Enter Password");
            return false;
        } else if (password.length() < 6) {
            editTextPassword.setError("Enter atleast six characters");
            return false;
        } else {
            return true;
        }
    }

}