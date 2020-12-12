package com.eflexsoft.easyclosest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.eflexsoft.easyclosest.databinding.ActivityChangeOutfitTextBinding;

public class ChangeOutfitTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_change_outfit_text);

        ActivityChangeOutfitTextBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_change_outfit_text);


    }
}