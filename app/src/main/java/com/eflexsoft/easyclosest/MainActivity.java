package com.eflexsoft.easyclosest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.eflexsoft.easyclosest.databinding.ActivityMainBinding;
import com.eflexsoft.easyclosest.fragment.EventFragment;
import com.eflexsoft.easyclosest.fragment.FavouriteFragment;
import com.eflexsoft.easyclosest.fragment.HomeFragment;
import com.eflexsoft.easyclosest.fragment.ProfileFragment;
import com.eflexsoft.easyclosest.fragment.UploadBottomSheetFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new HomeFragment()).commit();
        }

        activityMainBinding.nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.home:

                        fragment = new HomeFragment();

                        break;
                    case R.id.favourite:
                        fragment = new FavouriteFragment();

                        break;
                    case R.id.events:

                        fragment = new EventFragment();
                        break;

                    case R.id.profile:

                        fragment = new ProfileFragment();
                        break;

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment).commit();

                return true;
            }
        });

        activityMainBinding.nav.setBackground(null);

        activityMainBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadBottomSheetFragment bottomSheetFragment = new UploadBottomSheetFragment();
                bottomSheetFragment.show(getSupportFragmentManager(), "doUpload");
            }
        });

    }
}