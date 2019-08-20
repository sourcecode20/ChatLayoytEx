package com.example.chatlayoytex.ui.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatlayoytex.R;
import com.example.chatlayoytex.utils.Loader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class FireBaseExActivity extends AppCompatActivity {

    Loader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_base_ex);

        loader = new Loader(this);
        loader.show();
        updateObject();

    }



    void updateSingleValue(){

        FirebaseDatabase.getInstance().getReference()
                .child("Single").setValue("value")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        loader.dismiss();
                    }
                });

    }


    void updateObject(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("key1",12);
        map.put("key2","jhdgdshg");

        FirebaseDatabase.getInstance().getReference()
                .child("Ex")
                .child(FirebaseAuth.getInstance().getUid())
                .child("Key2")
                .push()
                .updateChildren(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        loader.dismiss();
                    }
                });



    }




}
