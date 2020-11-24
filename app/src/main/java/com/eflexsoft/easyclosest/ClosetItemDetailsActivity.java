package com.eflexsoft.easyclosest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.eflexsoft.easyclosest.R;
import com.eflexsoft.easyclosest.databinding.ActivityClosetItemDetailsBinding;
import com.eflexsoft.easyclosest.fragment.PickImageBottomSheetFragment;
import com.google.android.material.appbar.AppBarLayout;

public class ClosetItemDetailsActivity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_closet_item_details);

        intent = getIntent();

        ActivityClosetItemDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_closet_item_details);
        setSupportActionBar(binding.toolb);
        setTitle("");
        binding.toolb.setNavigationIcon(R.drawable.ic_left_arrow2);
        binding.toolb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


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


        String itemImageUrl = intent.getStringExtra("itemImageUrl");
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.color.brown);
        requestOptions.error(R.color.brown);

        Glide.with(this).load(itemImageUrl).apply(requestOptions).into(binding.itemImage);

        String category = intent.getStringExtra("category");
        String season = intent.getStringExtra("season");
        String note = intent.getStringExtra("note");

        binding.category.setText(category);
        binding.season.setText(season);

        if (!note.trim().isEmpty()) {
            binding.note.setText(note);
        } else {
            binding.note.setText("No note was added");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.closet_detail_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.delete:
//                doImageCrop();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this)
                        .setTitle("Confirm Delete")
                        .setMessage("are you sure want to delete this item from your closet?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                AlertDialog dialog = alertDialog.create();

//                dialog.show();

                break;
            case R.id.pick_image:

//                PickImageBottomSheetFragment fragment = new PickImageBottomSheetFragment();
//                fragment.show(getSupportFragmentManager(), "image");

                break;

        }

        return super.onOptionsItemSelected(item);


    }

}