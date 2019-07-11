package com.example.chatlayoytex.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.chatlayoytex.adapter.ViewPagerAdpater;
import com.example.chatlayoytex.fragment.AboutFragment;
import com.example.chatlayoytex.fragment.HomeFragment;
import com.example.chatlayoytex.R;
import com.example.chatlayoytex.fragment.SettingFragment;
import com.example.chatlayoytex.fragment.UserFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class NavigationActivity extends AppCompatActivity {

    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FrameLayout frameLayout;
    TabLayout tabLayout;
    ViewPager viewPager;
    List<Fragment> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_layout);


        findViewById();
        getFragments();
        viewPager();
        navigationView();

        replace(new HomeFragment(), null, false);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


    }

    private void navigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.Home:
                        Toast.makeText(NavigationActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        replace(new HomeFragment(), "a", true);
                        break;

                    case R.id.AllUsers:
                        Toast.makeText(NavigationActivity.this, "All Users", Toast.LENGTH_SHORT).show();
                        replace(new UserFragment(), "b", true);
                        break;

                    case R.id.Settings:
                        Toast.makeText(NavigationActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        replace(new SettingFragment(), "c", true);
                        break;
                    case R.id.About:
                        Toast.makeText(NavigationActivity.this, "About", Toast.LENGTH_SHORT).show();
                        replace(new AboutFragment(), "d", true);
                        break;

                }
                drawerLayout.closeDrawer(GravityCompat.START, true);
                return true;
            }
        });

    }

    private void findViewById() {
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation);
        frameLayout = findViewById(R.id.frame);
        tabLayout = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewpager);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void viewPager() {
        viewPager.setAdapter(new ViewPagerAdpater(getSupportFragmentManager(), list, this));
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

    private void getFragments() {

        list = new ArrayList<>();
        list.add(new HomeFragment());
        list.add(new UserFragment());

        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("All Users"));
    }

    private void replace(Fragment blankFragment, String tag, boolean status) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (status) {
            fragmentTransaction.replace(R.id.frame, blankFragment, tag);
            fragmentTransaction.addToBackStack(tag);
        } else
            fragmentTransaction.replace(R.id.frame, blankFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START, true);
        else
            super.onBackPressed();
    }

}

