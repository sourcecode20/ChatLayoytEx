package com.example.chatlayoytex.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatlayoytex.R;
import com.example.chatlayoytex.model.Users;
import com.example.chatlayoytex.ui.activity.ChatActivity;
import com.example.chatlayoytex.ui.activity.ProfileActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllUserAdapter extends FirebaseRecyclerAdapter<Users, AllUserAdapter.ListViewHolder> {
    private Context context;


    public AllUserAdapter(FirebaseRecyclerOptions<Users> options, Context context) {
        super(options);
        this.context = context;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allusers, parent, false);
        return new ListViewHolder(view);

    }

    @Override
    protected void onBindViewHolder(@NonNull ListViewHolder listViewHolder, int i, @NonNull Users users) {


        listViewHolder.name.setText(users.getName());
        Picasso.get().load("https://i0.wp.com/zblogged.com/wp-content/uploads/2019/02/FakeDP.jpeg").into(listViewHolder.image);


        listViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DialogPlus dialog = DialogPlus.newDialog(context)
                        .setContentHolder(new ViewHolder(R.layout.dialog))
                        .setGravity(Gravity.CENTER)
                        .create();
                dialog.show();

                LinearLayout layout = (LinearLayout) dialog.getHolderView();

                Button chat, profile;

                chat = layout.findViewById(R.id.chat);
                if (FirebaseAuth.getInstance().getUid().equals(getRef(i).getKey()))
                    chat.setVisibility(View.GONE);
                else
                    chat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                            Intent intent = new Intent(context, ChatActivity.class);
                            intent.putExtra("id", getRef(i).getKey().toString());
                            context.startActivity(intent);

                        }
                    });


                profile = layout.findViewById(R.id.profile);
                profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent = new Intent(context, ProfileActivity.class);
                        intent.putExtra("id",getRef(i).getKey().toString());
                        context.startActivity(intent);

                    }
                });
            }
        });
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CircleImageView image;
        LinearLayout layout;

        ListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.userName);
            image = itemView.findViewById(R.id.circleImageView);
            layout = itemView.findViewById(R.id.onClick);
        }
    }
}
