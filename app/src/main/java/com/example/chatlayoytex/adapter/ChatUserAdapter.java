package com.example.chatlayoytex.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatlayoytex.R;
import com.example.chatlayoytex.firebase.Constants;
import com.example.chatlayoytex.ui.activity.ChatActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatUserAdapter extends FirebaseRecyclerAdapter<Object, ChatUserAdapter.ListViewHolder> {
    private Context context;

    public ChatUserAdapter(@NonNull FirebaseRecyclerOptions options, Context context) {
        super(options);
        this.context = context;
    }


    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatusers, parent, false);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new ListViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatUserAdapter.ListViewHolder listViewHolder, int i, @NonNull Object users) {

        Picasso.get().load("https://i0.wp.com/zblogged.com/wp-content/uploads/2019/02/FakeDP.jpeg").into(listViewHolder.chatImage);

        FirebaseDatabase.getInstance().getReference()
                .child(Constants.Users.key)
                .child(getRef(i).getKey().toString())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listViewHolder.chatName.setText(dataSnapshot.child(Constants.Users.name).getValue().toString());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


       FirebaseDatabase.getInstance().getReference()
               .child(Constants.Chats.key)
               .child(FirebaseAuth.getInstance().getUid())
               .child(getRef(i).getKey().toString())
               .limitToLast(1)
               .addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                      listViewHolder.chatMessage.setText(dataSnapshot.getChildren().iterator().next().child(Constants.Chats.message).getValue().toString());

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {


                   }
               });




        listViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("id", getRef(i).getKey().toString());
                context.startActivity(intent);
            }
        });
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        TextView chatName, chatMessage;
        CircleImageView chatImage;
        LinearLayout layout;

        ListViewHolder(@NonNull View itemView) {
            super(itemView);
            chatName = itemView.findViewById(R.id.chatUserName);
            chatMessage = itemView.findViewById(R.id.chatUserMessage);
            chatImage = itemView.findViewById(R.id.chatuserimage);
            layout = itemView.findViewById(R.id.layout2);
        }
    }
}

