package com.eflexsoft.easyclosest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eflexsoft.easyclosest.databinding.ActivityChangeOutfitImageBinding;
import com.eflexsoft.easyclosest.model.UpdateImage;
import com.eflexsoft.easyclosest.viewmodel.ChangeOutfitImageViewModel;
import com.eflexsoft.easyclosest.viewmodel.EventViewModel;
import com.kroegerama.imgpicker.BottomSheetImagePicker;
import com.kroegerama.imgpicker.ButtonType;

import java.util.List;

public class ChangeOutfitImageActivity extends AppCompatActivity implements BottomSheetImagePicker.OnImagesSelectedListener {

    ActivityChangeOutfitImageBinding binding;
    ChangeOutfitImageViewModel viewModel;
    EventViewModel eventViewModel;
    Uri uri;

    Intent intent;

    String name;
    String imageUrl;
    boolean isEvent;
    long id;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_change_outfit_image);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_outfit_image);

        intent = getIntent();
        name = intent.getStringExtra("name");
        imageUrl = intent.getStringExtra("url");
        isEvent = intent.getBooleanExtra("isEvent", false);
        id = intent.getLongExtra("id", 0);
        position = intent.getIntExtra("position", -1);

        Glide.with(this).load(imageUrl).into(binding.img);

        binding.addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(ChangeOutfitImageActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED &&
                        ActivityCompat.checkSelfPermission(ChangeOutfitImageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                ) {

                    ActivityCompat.requestPermissions(ChangeOutfitImageActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);

                } else {

                    new BottomSheetImagePicker.Builder(getString(R.string.file_provider))
                            .cameraButton(ButtonType.Button)
                            .galleryButton(ButtonType.Button)
                            .singleSelectTitle(R.string.pick_single)
                            .peekHeight(R.dimen.peekHeight)
                            .columnSize(R.dimen.columnSize)
                            .requestTag("single")
                            .show(getSupportFragmentManager(), "gg");

                }
            }
        });

        viewModel = new ViewModelProvider(this).get(ChangeOutfitImageViewModel.class);
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        binding.uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uri != null) {

                    if (isEvent) {
                        eventViewModel.changeImage(imageUrl, name, id, position, uri);
                    } else {
                        viewModel.changeImage(imageUrl, name, id, position, uri);
                    }

                    Toast.makeText(ChangeOutfitImageActivity.this, "Uploading", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }

    @Override
    public void onImagesSelected(List<? extends Uri> list, String s) {

        uri = list.get(0);
        binding.img.setImageURI(uri);

    }
}