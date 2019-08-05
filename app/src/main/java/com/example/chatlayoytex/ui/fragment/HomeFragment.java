package com.example.chatlayoytex.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.chatlayoytex.R;
import com.example.chatlayoytex.adapter.ViewPagerAdpater;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private ViewPager viewPager;

    public HomeFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        TabLayout tabLayout = view.findViewById(R.id.tab);
        viewPager = view.findViewById(R.id.viewpager);

        List<Fragment> list = new ArrayList<>();
        list.add(new ChatFragment());
        list.add(new UserFragment());

        tabLayout.addTab(tabLayout.newTab().setText("Chat"));

        tabLayout.addTab(tabLayout.newTab().setText("All Users"));


        viewPager.setAdapter(new ViewPagerAdpater(getChildFragmentManager(), list, getContext()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

}
