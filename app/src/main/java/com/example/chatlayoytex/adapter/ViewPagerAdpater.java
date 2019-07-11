package com.example.chatlayoytex.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chatlayoytex.fragment.HomeFragment;
import com.example.chatlayoytex.fragment.UserFragment;

import java.util.List;

public class ViewPagerAdpater extends FragmentPagerAdapter {
    private List<Fragment> list;
    private Context context;

    public ViewPagerAdpater(FragmentManager fm) {
        super(fm);
    }

    public ViewPagerAdpater(FragmentManager fm, List<Fragment> list, Context context) {
        super(fm);
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new UserFragment();
        }

        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Home";
            case 1:
                return "All Users";
        }

        return super.getPageTitle(position);
    }
}
