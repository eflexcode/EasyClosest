package com.eflexsoft.easyclosest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
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
import com.eflexsoft.easyclosest.fragment.DeleteBottomSheetFragment;
import com.eflexsoft.easyclosest.fragment.PickImageBottomSheetFragment;
import com.eflexsoft.easyclosest.viewmodel.ClosetItemViewModel;
import com.google.android.material.appbar.AppBarLayout;

public class ClosetItemDetailsActivity extends AppCompatActivity {

    Intent intent;
    ClosetItemViewModel viewModel;


    String category;
    String season;
    String note;
    String id;
    String itemImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_closet_item_details);

        intent = getIntent();
        viewModel = new ViewModelProvider(this).get(ClosetItemViewModel.class);

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


        itemImageUrl = intent.getStringExtra("itemImageUrl");
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.color.brown);
        requestOptions.error(R.color.brown);

        Glide.with(this).load(itemImageUrl).apply(requestOptions).into(binding.itemImage);

        category = intent.getStringExtra("category");
        season = intent.getStringExtra("season");
        note = intent.getStringExtra("note");
        id = String.valueOf(intent.getLongExtra("id", 0));

        binding.category.setText(category);
        binding.season.setText(season);

        if (!note.trim().isEmpty()) {
            binding.note.setText(note);
        } else {
            binding.note.setText("No note was added");
        }


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Just a sec");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        viewModel.getIsDelete().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {

                    progressDialog.show();
                    viewModel.deleteItem(category, id);

                    //isdelete

                } else {
                }

            }
        });

        viewModel.getIsDeleteInSuccessful().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {

                    progressDialog.dismiss();
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ClosetItemDetailsActivity.this)
                            .setTitle("Delete successful")
                            .setMessage("refresh home for updated closet")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });

                    AlertDialog dialog = alertDialog.create();

                    dialog.show();

                    //isdeleted

                } else {
                    progressDialog.dismiss();
                }

            }
        });

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
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this)
//                        .setTitle("Confirm Delete")
//                        .setMessage("are you sure want to delete this item from your closet?")
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        }).setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        });
//
//                AlertDialog dialog = alertDialog.create();

//                dialog.show();
                DeleteBottomSheetFragment fragment = new DeleteBottomSheetFragment();
                fragment.show(getSupportFragmentManager(), "delete");

                break;
            case R.id.edit:


                Intent intent = new Intent(this, UpdateClosetItemActivity.class);
                intent.putExtra("itemImageUrl",itemImageUrl);
                intent.putExtra("category",category);
                intent.putExtra("season", season);
                intent.putExtra("note", note);
                intent.putExtra("id",id);

                startActivity(intent);


                break;

        }

        return super.onOptionsItemSelected(item);


    }

}