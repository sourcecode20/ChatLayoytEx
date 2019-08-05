package com.example.chatlayoytex.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

import com.example.chatlayoytex.R;
import com.example.chatlayoytex.firebase.Constants;
import com.example.chatlayoytex.ui.fragment.AboutFragment;
import com.example.chatlayoytex.ui.fragment.HomeFragment;
import com.example.chatlayoytex.ui.fragment.SettingFragment;
import com.example.chatlayoytex.ui.fragment.UserFragment;
import com.example.chatlayoytex.utils.Loader;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NavigationActivity extends AppCompatActivity {

    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FrameLayout frameLayout;
    TabLayout tabLayout;
    Loader loader;
    TextView headerName, headerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_layout);


        init();

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
                    case R.id.Logout:
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(NavigationActivity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(NavigationActivity.this, LoginActivity.class));
                        loader.show();
                        finish();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START, true);
                return true;
            }
        });

        RelativeLayout layout = (RelativeLayout) navigationView.getHeaderView(0);
        TextView headerName, headerEmail;
        headerName = layout.findViewById(R.id.userHeaderName);
        headerEmail = layout.findViewById(R.id.userHeaderEmail);
        FirebaseDatabase.getInstance().getReference()
                .child(Constants.Users.key)
                .child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        headerEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
                        headerName.setText(dataSnapshot.child(Constants.Users.name).getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    private void init() {
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation);
        frameLayout = findViewById(R.id.frame);
        tabLayout = findViewById(R.id.tab);
        headerName = findViewById(R.id.userHeaderName);
        headerEmail = findViewById(R.id.userHeaderEmail);
        loader = new Loader(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

