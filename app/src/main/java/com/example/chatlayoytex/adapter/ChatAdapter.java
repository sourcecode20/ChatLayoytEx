package com.example.chatlayoytex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatlayoytex.R;
import com.example.chatlayoytex.callback.ResCallback;
import com.example.chatlayoytex.model.Chats;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ChatAdapter extends FirebaseRecyclerAdapter<Chats, ChatAdapter.ListViewHolder> {

    private Context context;
    private ResCallback resCallback;


    public ChatAdapter(@NonNull FirebaseRecyclerOptions<Chats> options, Context context, ResCallback resCallback) {
        super(options);
        this.context = context;
        this.resCallback = resCallback;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_view, parent, false);
        return new ListViewHolder(view);
    }


    @Override
    public void onDataChanged() {
        super.onDataChanged();
        resCallback.callback(true);
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatAdapter.ListViewHolder listViewHolder, int i, @NonNull Chats chats) {

         if(chats.getMessage_type().equals("receive")){
             listViewHolder.chatreceive.setText(chats.getMessage());
             listViewHolder.chatsend.setVisibility(View.GONE);
         }
         else {
             listViewHolder.chatsend.setText(chats.getMessage());
             listViewHolder.chatreceive.setVisibility(View.GONE);
         }
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        TextView chatsend, chatreceive;

        ListViewHolder(@NonNull View itemView) {
            super(itemView);
            chatsend = itemView.findViewById(R.id.chatsend);
            chatreceive = itemView.findViewById(R.id.chatreceive);
        }
    }
}
