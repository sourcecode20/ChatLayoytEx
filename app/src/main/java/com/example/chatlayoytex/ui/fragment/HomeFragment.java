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
    private TabLayout tabLayout;

    public HomeFragment() {
        // Required empty public constructor

    }

    @Override
    public void onResume() {
        super.onResume();

        List<Fragment> list = new ArrayList<>();
        list.add(new ChatFragment());
        list.add(new AllUserFragment());


        viewPager.setOffscreenPageLimit(2);
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        tabLayout = view.findViewById(R.id.tab);
        viewPager = view.findViewById(R.id.viewpager);


        return view;
    }

}
