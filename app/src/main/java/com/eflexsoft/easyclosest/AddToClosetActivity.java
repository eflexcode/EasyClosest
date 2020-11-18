package com.eflexsoft.easyclosest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.eflexsoft.easyclosest.databinding.ActivityAddToClosetBinding;
import com.eflexsoft.easyclosest.fragment.PickImageBottomSheetFragment;
import com.eflexsoft.easyclosest.viewmodel.PickImageViewModel;

import java.io.ByteArrayOutputStream;

public class AddToClosetActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    PickImageViewModel viewModel;
    int imageFileRequestCode;
    int imageCamera;
    Uri uri;
    ActivityAddToClosetBinding binding;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_to_closet);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_to_closet);

        setSupportActionBar(binding.toolb);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_array, android.R.layout.simple_spinner_item);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> arrayAdapterSeason = ArrayAdapter.createFromResource(this, R.array.weather, android.R.layout.simple_spinner_item);

        arrayAdapterSeason.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.categorySpinner.setAdapter(arrayAdapter);
        binding.categorySpinner.setOnItemSelectedListener(this);

        binding.weatherSpinner.setAdapter(arrayAdapterSeason);
        binding.weatherSpinner.setOnItemSelectedListener(this);
        binding.toolb.setNavigationIcon(R.drawable.ic_left_arrow2);
        binding.toolb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                overridePendingTransition(R.anim.slide_in_rigth,R.anim.slide_out_left);
            }
        });

        viewModel = new ViewModelProvider(this).get(PickImageViewModel.class);
        viewModel.getIsCamera().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {
                    pickImageCamera();
//                    Toast.makeText(AddToClosetActivity.this, "rrrrrrrrrrt", Toast.LENGTH_SHORT).show();
                } else {
                    pickImageFile();
                }

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.closet_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.crop:
                break;
            case R.id.pick_image:

                PickImageBottomSheetFragment fragment = new PickImageBottomSheetFragment();
                fragment.show(getSupportFragmentManager(), "image");

                break;

        }

        return super.onOptionsItemSelected(item);


    }

    void pickImageFile() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        startActivityForResult(intent, imageFileRequestCode);
    }

    void pickImageCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, imageCamera);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == imageFileRequestCode && resultCode == RESULT_OK) {

            uri = data.getData();
            binding.imagePH.setVisibility(View.GONE);
            binding.maindImage.setImageURI(uri);

        }

        if (requestCode == imageCamera && resultCode == RESULT_OK) {

            Bundle bundle = data.getExtras();

            bitmap = (Bitmap) bundle.get("data");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

            binding.imagePH.setVisibility(View.GONE);
            binding.maindImage.setImageBitmap(bitmap);

        }

    }
}