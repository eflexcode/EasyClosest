package com.eflexsoft.easyclosest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.eflexsoft.easyclosest.brodcastreceiver.SendDailyAlarm;
import com.eflexsoft.easyclosest.databinding.ActivityMainBinding;
import com.eflexsoft.easyclosest.fragment.EventFragment;
import com.eflexsoft.easyclosest.fragment.FavouriteFragment;
import com.eflexsoft.easyclosest.fragment.HomeFragment;
import com.eflexsoft.easyclosest.fragment.ProfileFragment;
import com.eflexsoft.easyclosest.fragment.UploadBottomSheetFragment;
import com.eflexsoft.easyclosest.viewmodel.AdsViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Fragment fragment;
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        AdsViewModel ads = new ViewModelProvider(this).get(AdsViewModel.class);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.INTERNET}, 5);

        }


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

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9552597639357298/5372002177");
        mInterstitialAd.loadAd(new AdRequest.Builder().tagForChildDirectedTreatment(true).build());

        ads.showAds().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }
                }
            }
        });

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(this, SendDailyAlarm.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,34,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,7);
        calendar.set(Calendar.MINUTE,30);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

    }
}