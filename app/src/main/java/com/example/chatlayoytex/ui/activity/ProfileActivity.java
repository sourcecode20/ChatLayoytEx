package com.example.chatlayoytex.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.chatlayoytex.R;
import com.example.chatlayoytex.callback.DialogResponceCallback;
import com.example.chatlayoytex.firebase.Constants;
import com.example.chatlayoytex.utils.EditDialog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    CircleImageView imageView;
    Toolbar toolbar1;
    TextView email, name, age, mobile, address;
    Button cancel_name, save_name;
    ImageView editName, editAge, editMobile, editAddress;
    Context context;

    EditText editUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();
        dialog_name();
        Picasso.get().load("https://i0.wp.com/zblogged.com/wp-content/uploads/2019/02/FakeDP.jpeg").into(imageView);

        FirebaseDatabase.getInstance().getReference()
                .child(Constants.Users.key)
                .child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
                        name.setText(dataSnapshot.child(Constants.Users.name).getValue().toString());
                        age.setText(dataSnapshot.child(Constants.Users.age).getValue().toString());
                        mobile.setText(dataSnapshot.child(Constants.Users.mobile).getValue().toString());
                        address.setText(dataSnapshot.child(Constants.Users.address).getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    public void dialog_name() {
        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditDialog editDialog = new EditDialog(ProfileActivity.this, new DialogResponceCallback<String>() {
                    @Override
                    public void callback(String s) {

                        HashMap<String, Object> map = new HashMap<>();
                        map.put(Constants.Users.name, s);

                        FirebaseDatabase.getInstance().getReference()
                                .child(Constants.Users.key)
                                .child(FirebaseAuth.getInstance().getUid())
                                .updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                });
                    }
                });
                editDialog.show();
            }
        });
        editAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDialog editDialog1 = new EditDialog(ProfileActivity.this, new DialogResponceCallback<String>() {
                    @Override
                    public void callback(String s) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put(Constants.Users.age, s);
                        FirebaseDatabase.getInstance().getReference()
                                .child(Constants.Users.key)
                                .child(FirebaseAuth.getInstance().getUid())
                                .updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                });
                    }
                });
                editDialog1.show();
            }
        });
        editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDialog editDialog2 = new EditDialog(ProfileActivity.this, new DialogResponceCallback<String>() {
                    @Override
                    public void callback(String s) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put(Constants.Users.address, s);
                        FirebaseDatabase.getInstance().getReference()
                                .child(Constants.Users.key)
                                .child(FirebaseAuth.getInstance().getUid())
                                .updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                });
                    }
                });
                editDialog2.show();
            }
        });
        editMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDialog editDialog3 = new EditDialog(ProfileActivity.this, new DialogResponceCallback<String>() {
                    @Override
                    public void callback(String s) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put(Constants.Users.mobile, s);
                        FirebaseDatabase.getInstance().getReference()
                                .child(Constants.Users.key)
                                .child(FirebaseAuth.getInstance().getUid())
                                .updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                });
                    }
                });
                editDialog3.show();
            }
        });
    }

    private void init() {
        imageView = findViewById(R.id.userImage2);
        email = findViewById(R.id.userEmailId);
        name = findViewById(R.id.userProfileName);
        age = findViewById(R.id.userProfileAge);
        mobile = findViewById(R.id.userProfileMobile);
        address = findViewById(R.id.userProfileAddress);
        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.edieAge);
        editMobile = findViewById(R.id.editMobile);
        editAddress = findViewById(R.id.editAddress);
        editUserName = findViewById(R.id.edit);
        cancel_name = findViewById(R.id.cancel);
        save_name = findViewById(R.id.save);

        toolbar1 = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setCustomView(R.layout.fragment_user);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }
}
