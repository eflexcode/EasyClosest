package com.eflexsoft.easyclosest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.eflexsoft.easyclosest.databinding.ActivityCropBinding;
import com.theartofdev.edmodo.cropper.CropImageView;

public class CropActivity extends AppCompatActivity {

    Intent intent;
    Bitmap bitmap;

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

        binding.cropImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
            @Override
            public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {

            }
        });

        binding.crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = binding.cropImageView.getCroppedImage(600, 600);

                if (bitmap != null) {
                    binding.cropImageView.setImageBitmap(bitmap);
                }
            }
        });

        binding.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bitmap != null) {
                    binding.cropImageView.setImageBitmap(bitmap);

                    Intent intent = new Intent();
                    intent.putExtra("doneBitmap", bitmap);
                    setResult(RESULT_OK,intent);
                    finish();

                }
            }
        });

    }
}