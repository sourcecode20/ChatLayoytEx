package com.example.chatlayoytex.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatlayoytex.R;
import com.example.chatlayoytex.adapter.ChatUserAdapter;
import com.example.chatlayoytex.firebase.Constants;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    private ChatUserAdapter chatUserAdapter;
    private View view;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_chat, container, false);

        adapter();

        return view;

    }


    private void adapter() {
        RecyclerView recyclerView2 = view.findViewById(R.id.recyclerview2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

        chatUserAdapter = new ChatUserAdapter(new FirebaseRecyclerOptions.Builder<Object>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child(Constants.Chats.key).child(FirebaseAuth.getInstance().getUid()), Object.class)
                .build(), getActivity());
        recyclerView2.setAdapter(chatUserAdapter);
        chatUserAdapter.startListening();

    }

    @Override
    public void onPause() {
        super.onPause();
        chatUserAdapter.stopListening();
    }

}
