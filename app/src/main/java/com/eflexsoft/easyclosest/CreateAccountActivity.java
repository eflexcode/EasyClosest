package com.eflexsoft.easyclosest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.eflexsoft.easyclosest.databinding.ActivityCreateAccountBinding;
import com.eflexsoft.easyclosest.databinding.ActivityLoginBinding;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_create_account);

        ActivityCreateAccountBinding activityCreateAccountBinding = DataBindingUtil.setContentView(this,R.layout.activity_create_account);
        activityCreateAccountBinding.toolb.setNavigationIcon(R.drawable.ic_left_arrow);
        activityCreateAccountBinding.toolb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                overridePendingTransition(R.anim.slide_in_rigth,R.anim.slide_out_left);
            }
        });
    }
}