package com.eflexsoft.easyclosest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.eflexsoft.easyclosest.databinding.ActivityCropBinding;
import com.theartofdev.edmodo.cropper.CropImageView;

public class CropActivity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_crop);

        ActivityCropBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_crop);

        intent = getIntent();

//        binding.cropImageView.getCroppedImageAsync();
//        binding.cropImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
//            @Override
//            public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
//
//            }
//        });

//        binding.cropImageView.getCroppedImage();

        if (intent.hasExtra("bitmap")) {
            Bitmap bitmap = (Bitmap) intent.getParcelableExtra("bitmap");
            binding.cropImageView.setImageBitmap(bitmap);
        } else {
            Uri uri = (Uri) intent.getParcelableExtra("uri");
            binding.cropImageView.setImageUriAsync(uri);
        }

    }
}