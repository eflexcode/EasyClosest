package com.eflexsoft.easyclosest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_acivity);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                if (firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                }else {
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                }


            }
        },3000);
    }
}