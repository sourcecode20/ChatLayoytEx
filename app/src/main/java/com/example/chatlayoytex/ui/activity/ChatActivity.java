package com.example.chatlayoytex.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatlayoytex.R;
import com.example.chatlayoytex.adapter.ChatAdapter;
import com.example.chatlayoytex.callback.ResCallback;
import com.example.chatlayoytex.firebase.Constants;
import com.example.chatlayoytex.model.Chats;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ChatActivity extends AppCompatActivity {
    private EditText message;
    ImageView send;
    Loader loader;
    Toolbar toolbar;
    String chatname;
    TextView chatsend, chatreceive;
    ChatAdapter chatAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        init();

        adapter();

        loader = new Loader(this);

        message = findViewById(R.id.Message);
        send = findViewById(R.id.Send);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });

    }

    private void adapter() {

        recyclerView = findViewById(R.id.chatrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        chatAdapter = new ChatAdapter(new FirebaseRecyclerOptions.Builder<Chats>()
                .setQuery(FirebaseDatabase.getInstance().getReference()
                        .child(Constants.Chats.key)
                        .child(FirebaseAuth.getInstance().getUid())
                        .child(getIntent().getStringExtra("id")), Chats.class).build(), getApplicationContext(), new ResCallback<Boolean>() {
            @Override
            public void callback(Boolean b) {
                if (b) {
                    recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
                }

            }
        });

        recyclerView.setAdapter(chatAdapter);
        chatAdapter.startListening();

    }


    private void init() {
        toolbar = findViewById(R.id.chattoolbar);
        setSupportActionBar(toolbar);

        chatsend = findViewById(R.id.chatsend);
        chatreceive = findViewById(R.id.chatreceive);

        Intent intent = getIntent();
        FirebaseDatabase.getInstance().getReference()
                .child(Constants.Users.key)
                .child(intent.getStringExtra("id"))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        chatname = (String) dataSnapshot.child(Constants.Users.name).getValue().toString();
                        toolbar.setTitle(chatname);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void send() {
        loader.startLoading();

        String msgbox = "";
        msgbox = message.getText().toString();

        String t = msgbox.trim();

        Intent intent = getIntent();

        if (!TextUtils.isEmpty(t) && t.length() > 0) {
            HashMap<String, Object> map = new HashMap<>();
            map.put(Constants.Chats.message, message.getText().toString());
            map.put(Constants.Chats.message_type, "send");
            FirebaseDatabase.getInstance().getReference()
                    .child(Constants.Chats.key)
                    .child(FirebaseAuth.getInstance().getUid())
                    .child(intent.getStringExtra("id"))
                    .push()
                    .updateChildren(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            HashMap<String, Object> map1 = new HashMap<>();
                            map1.put(Constants.Chats.message, message.getText().toString());
                            map1.put(Constants.Chats.message_type, "receive");
                            FirebaseDatabase.getInstance().getReference()
                                    .child(Constants.Chats.key)
                                    .child(intent.getStringExtra("id"))
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .push()
                                    .updateChildren(map1)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            message.getText().clear();
                                            recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
                                        }
                                    });
                            message.getText().clear();
                        }
                    });
        } else
            Toast.makeText(getApplicationContext(), "enter message", Toast.LENGTH_SHORT).show();
    }

}
