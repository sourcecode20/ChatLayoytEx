package com.example.chatlayoytex.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatlayoytex.R;
import com.example.chatlayoytex.adapter.AllUserAdapter;
import com.example.chatlayoytex.firebase.Constants;
import com.example.chatlayoytex.model.Users;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    private AllUserAdapter allUserAdapter;
    private View view;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);


        adapter();

        return view;

    }

    private void adapter() {

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        allUserAdapter = new AllUserAdapter(new FirebaseRecyclerOptions.Builder<Users>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").orderByChild(Constants.Users.name), Users.class)
                .build(), getActivity());
        recyclerView.setAdapter(allUserAdapter);
        allUserAdapter.startListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        allUserAdapter.stopListening();
    }


}
