package com.eflexsoft.easyclosest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.eflexsoft.easyclosest.R;
import com.eflexsoft.easyclosest.databinding.ActivityClosetItemDetailsBinding;
import com.google.android.material.appbar.AppBarLayout;

public class ClosetItemDetailsActivity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_closet_item_details);

        intent = getIntent();

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

//        getWindow().restoreHierarchyState();

        ActivityClosetItemDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_closet_item_details);

        binding.appBar.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

//                if (verticalOffset != 0) {
//
//                    setTheme(R.style.Tran2);
//                } else {
//                    setTheme(R.style.Tran);
//                }

            }
        });


//        binding.toolb.setNavigationIcon(R.drawable.ic_left_arrow2);
//        binding.toolb.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        String itemImageUrl = intent.getStringExtra("itemImageUrl");
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.color.brown);
        requestOptions.error(R.color.brown);
//        requestOptions.

        Glide.with(this).load(itemImageUrl).apply(requestOptions).into(binding.itemImage);

    }
}