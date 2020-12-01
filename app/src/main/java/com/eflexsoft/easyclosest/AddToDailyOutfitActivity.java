package com.eflexsoft.easyclosest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;


import com.eflexsoft.easyclosest.adapter.SlideImageAdapter;
import com.eflexsoft.easyclosest.databinding.ActivityAddToClosetBinding;
import com.eflexsoft.easyclosest.databinding.ActivityAddToDailyOutfitBinding;
import com.eflexsoft.easyclosest.fragment.PickDateFragment;
import com.eflexsoft.easyclosest.fragment.PickImageBottomSheetFragment;
import com.eflexsoft.easyclosest.model.SlideImageItem;
import com.eflexsoft.easyclosest.viewmodel.AddToClosetViewModel;
import com.eflexsoft.easyclosest.viewmodel.AddToDailyOutfitViewModel;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kroegerama.imgpicker.BottomSheetImagePicker;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddToDailyOutfitActivity extends AppCompatActivity implements BottomSheetImagePicker.OnImagesSelectedListener, DatePickerDialog.OnDateSetListener {

    SlideImageAdapter slideImageAdapter;
    ActivityAddToDailyOutfitBinding binding;
    List<Uri> uriList = new ArrayList<>();
    //    BSImagePicker bsImagePicker;
    String season;
    AddToDailyOutfitViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_to_daily_outfit);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_to_daily_outfit);

        setSupportActionBar(binding.toolb);

        viewModel = new ViewModelProvider(this).get(AddToDailyOutfitViewModel.class);

        binding.toolb.setNavigationIcon(R.drawable.ic_left_arrow2);
        binding.toolb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        bsImagePicker = new BSImagePicker.Builder(getString(R.string.file_provider))
//                .isMultiSelect()
//                .setMinimumMultiSelectCount(3)
//                .setMaximumMultiSelectCount(6)
//                .disableOverSelectionMessage()
//                .build();

//        slideImageAdapter = new SlideImageAdapter();
        ArrayAdapter<CharSequence> arrayAdapterSeason = ArrayAdapter.createFromResource(this, R.array.weather, android.R.layout.simple_spinner_item);

        arrayAdapterSeason.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.weatherSpinner.setAdapter(arrayAdapterSeason);
        binding.weatherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                season = arrayAdapterSeason.getItem(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.loginContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String note = binding.note.getText().toString();
                String date = binding.date.getText().toString();


                if (season.equals("Season")) {
                    Toast.makeText(AddToDailyOutfitActivity.this, "Invalid season", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (note.trim().isEmpty()) {
                    Toast.makeText(AddToDailyOutfitActivity.this, "Note is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (date.trim().isEmpty()) {
                    Toast.makeText(AddToDailyOutfitActivity.this, "Please pick a date", Toast.LENGTH_SHORT).show();
                    return;
                }
//                FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

//                StorageReference storageReference = firebaseStorage.getReference("sdfd");
//                storageReference.p

                if (uriList.size() == 0) {
                    Toast.makeText(AddToDailyOutfitActivity.this, "Please add an image", Toast.LENGTH_SHORT).show();
                    return;
                }

                binding.proB.setVisibility(View.VISIBLE);
                binding.continueText.setVisibility(View.GONE);

                viewModel.addToOutfit(uriList, season, note, date);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddToDailyOutfitActivity.this)
                                .setTitle("Upload status")
                                .setMessage("upload will continue in background refresh screen for new item.")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        onBackPressed();
                                    }
                                });

                        AlertDialog dialog = alertDialog.create();
                        dialog.show();
                    }
                }, 7000);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.outfit_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.pick_image_file:
//                doImageCrop();

//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
//                intent.setType("image/*");
//                startActivityForResult(Intent.createChooser(intent,"Continue with"),2);

//                Intent intent = new Intent(this, ImagePickerActivity.class);
//                startActivityForResult(intent,23);

                if (ActivityCompat.checkSelfPermission(AddToDailyOutfitActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED &&
                        ActivityCompat.checkSelfPermission(AddToDailyOutfitActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                ) {

                    ActivityCompat.requestPermissions(AddToDailyOutfitActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);

                } else {

                    new BottomSheetImagePicker.Builder(getString(R.string.file_provider))
                            .multiSelect(4, 6)
                            .multiSelectTitles(
                                    R.plurals.pick_multi,
                                    R.plurals.pick_multi_more,
                                    R.string.pick_multi_limit
                            )
                            .peekHeight(R.dimen.peekHeight)
                            .columnSize(R.dimen.columnSize)
                            .requestTag("multi")
                            .show(getSupportFragmentManager(), "gaga");

                }

                break;

            case R.id.pick_date:

                PickDateFragment pickDateFragment = new PickDateFragment();
                pickDateFragment.show(getSupportFragmentManager(), "pick");

                break;

        }

        return true;

    }

    @Override
    public void onImagesSelected(List<? extends Uri> list, String s) {

        uriList.clear();

        uriList.addAll(list);
        slideImageAdapter = new SlideImageAdapter(uriList, binding.v2);
        binding.v2.setAdapter(slideImageAdapter);

        binding.v2.setClipToPadding(false);
        binding.v2.setClipChildren(false);
        binding.v2.setOffscreenPageLimit(3);
        binding.v2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        binding.v2.setPageTransformer(compositePageTransformer);
        binding.noImg.setVisibility(View.GONE);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        binding.date.setText(date);

    }
}