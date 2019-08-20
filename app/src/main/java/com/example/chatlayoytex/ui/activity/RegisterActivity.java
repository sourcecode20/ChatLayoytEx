package com.example.chatlayoytex.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.chatlayoytex.R;
import com.example.chatlayoytex.firebase.Constants;
import com.example.chatlayoytex.utils.Loader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    Button signupBtn;
    TextView login;
    EditText editTextEmail2, editTextPassword2, editTextName, editTextAge, editTextMobile, editTextAddress;
    Toolbar toolbar2;
    Loader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        signup_listener();
        login_listener();


    }

    private void login_listener() {
        login = (TextView) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void signup_listener() {
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loader.show();
                editTextEmail2 = findViewById(R.id.editTextEmail2);
                editTextPassword2 = findViewById(R.id.editTextPassword2);
                editTextName = findViewById(R.id.editTextName);
                editTextAge = findViewById(R.id.editTextAge);
                editTextMobile = findViewById(R.id.editTextMobile);
                editTextAddress = findViewById(R.id.editTextAddress);


                final String email2, password2, name, age, mobile, address;
                email2 = editTextEmail2.getText().toString();
                password2 = editTextPassword2.getText().toString();
                name = editTextName.getText().toString();
                age = editTextAge.getText().toString();
                mobile = editTextMobile.getText().toString();
                address = editTextAddress.getText().toString();
                Log.i("sdbchjsbdf", "signup_listener: ");

                if (validation(email2, password2, name, age, mobile, address)) {
                    Log.i("sdbchjsbdf", "validation: ");

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(editTextEmail2.getText().toString(), editTextPassword2.getText().toString())
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    Log.i("sdbchjsbdf", "createUserWithEmailAndPassword: ");


                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put(Constants.Users.name, editTextName.getText().toString());
                                    map.put(Constants.Users.age, editTextAge.getText().toString());
                                    map.put(Constants.Users.mobile, editTextMobile.getText().toString());
                                    map.put(Constants.Users.address, editTextAddress.getText().toString());

                                    if (FirebaseAuth.getInstance().getUid() != null)
                                        FirebaseDatabase.getInstance().getReference()
                                                .child(Constants.Users.key)
                                                .child(FirebaseAuth.getInstance().getUid())
                                                .updateChildren(map)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Log.i("sdbchjsbdf", "addOnCompleteListener: ");


                                                        Toast.makeText(RegisterActivity.this, "Successfully Sign Up", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(RegisterActivity.this, NavigationActivity.class));
                                                        loader.dismiss();
                                                        finish();

                                                    }
                                                });
                                    loader.dismiss();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("sdbchjsbdf", "onFailure : ");
                        }
                    });
                } else {
                    loader.dismiss();

                }

            }
        });
    }

    private void init() {

        toolbar2 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        getSupportActionBar().setTitle("Register");

        loader = new Loader(this);
        signupBtn = findViewById(R.id.signupBtn);

    }

    private boolean validation(String email2, String password2, String name, String age, String mobile, String address) {
        String emailPattern2 = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
        String agePattern = "^(0|[1-9][0-9]*)$";
        String mobilePattern = "(0/91)?[7-9][0-9]{9}";

        if (TextUtils.isEmpty(name)) {
            editTextName.setError("Enter Name");
            return false;
        } else if (TextUtils.isEmpty(age)) {
            editTextAge.setError("Enter Age");
            return false;
        } else if (!age.matches(agePattern)) {
            editTextAge.setError("Enter Correct Age");
            return false;
        } else if (TextUtils.isEmpty(mobile)) {
            editTextMobile.setError("Enter Mobile Number");
            return false;
        } else if (!mobile.matches(mobilePattern)) {
            editTextMobile.setError("Enter correct mobile number");
            return false;
        } else if (TextUtils.isEmpty(address)) {
            editTextAddress.setError("Enter Address");
            return false;
        } else if (TextUtils.isEmpty(email2)) {
            editTextEmail2.setError("Enter Email address");
            return false;
        } else if (!email2.matches(emailPattern2)) {
            editTextEmail2.setError("Enter valid Email address");
            return false;
        } else if (TextUtils.isEmpty(password2)) {
            editTextPassword2.setError("Enter Password");
            return false;
        } else if (password2.length() < 6) {
            editTextPassword2.setError("Enter atleast six characters");
            return false;
        } else {
            return true;
        }
    }
}