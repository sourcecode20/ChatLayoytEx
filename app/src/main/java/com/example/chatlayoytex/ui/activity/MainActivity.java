package com.example.chatlayoytex.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatlayoytex.R;
import com.example.chatlayoytex.utils.Loader;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Loader loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int splash = 1;
        loader = new Loader(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    startActivity(new Intent(MainActivity.this, NavigationActivity.class));


                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));

                }
            }
        }, splash * 1000);
    }
}
