package com.eflexsoft.easyclosest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.eflexsoft.easyclosest.adapter.ImageItemAdapter;
import com.eflexsoft.easyclosest.databinding.ActivityAddToDailyOutfitBinding;
import com.eflexsoft.easyclosest.databinding.ActivityOutfitDatiledBinding;
import com.eflexsoft.easyclosest.model.ImageItem;

import java.util.ArrayList;
import java.util.List;

public class OutfitDatiledActivity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_outfit_datiled);

        ActivityOutfitDatiledBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_outfit_datiled);

        intent = getIntent();

        long id = intent.getLongExtra("id", 0);
        String note = intent.getStringExtra("note");
        String season = intent.getStringExtra("season");
        String date = intent.getStringExtra("date");
        String imageUrl1 = intent.getStringExtra("imageUrl1");
        String imageUrl2 = intent.getStringExtra("imageUrl2");
        String imageUrl3 = intent.getStringExtra("imageUrl3");
        String imageUrl4 = intent.getStringExtra("imageUrl4");
        String imageUrl5 = intent.getStringExtra("imageUrl5");
        String imageUrl6 = intent.getStringExtra("imageUrl6");


        List<ImageItem> imageItems = new ArrayList<>();
        imageItems.add(new ImageItem(imageUrl1));
        imageItems.add(new ImageItem(imageUrl2));
        imageItems.add(new ImageItem(imageUrl3));
        imageItems.add(new ImageItem(imageUrl4));

        if (imageUrl5 != null) {
            imageItems.add(new ImageItem(imageUrl5));
        }
        if (imageUrl6 != null) {
            imageItems.add(new ImageItem(imageUrl6));
        }

        ImageItemAdapter imageItemAdapter = new ImageItemAdapter(imageItems,this);
        binding.viewPager.setAdapter(imageItemAdapter);

    }
}