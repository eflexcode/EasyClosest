package com.eflexsoft.easyclosest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.eflexsoft.easyclosest.viewmodel.AddToClosetViewModel;
import com.eflexsoft.easyclosest.viewmodel.PickImageViewModel;

import java.io.ByteArrayOutputStream;

public class AddToClosetActivity extends AppCompatActivity {

    PickImageViewModel viewModel;
    int imageFileRequestCode = 7;
    int imageCamera = 9;
    Uri uri;
    ActivityAddToClosetBinding binding;
    Bitmap bitmap;
    Bitmap donBitmap;
    boolean isImageCropped;

    String category;
    String season;

    AddToClosetViewModel closetViewModel;

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
        binding.categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                category = arrayAdapter.getItem(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

                    if (ActivityCompat.checkSelfPermission(AddToClosetActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AddToClosetActivity.this, new String[]{Manifest.permission.CAMERA}, 3);
                    } else {
                        pickImageCamera();
                    }

                } else {

                    if (ActivityCompat.checkSelfPermission(AddToClosetActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED &&
                            ActivityCompat.checkSelfPermission(AddToClosetActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                    ) {

                        ActivityCompat.requestPermissions(AddToClosetActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);

                    } else {
                        pickImageFile();
                    }

                }

            }
        });

        closetViewModel = new ViewModelProvider(this).get(AddToClosetViewModel.class);
        closetViewModel.isSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {

                    Toast.makeText(AddToClosetActivity.this, "upload successful", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    binding.proB.setVisibility(View.GONE);
                    binding.continueText.setVisibility(View.VISIBLE);

                }
            }
        });

        binding.loginContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String note = binding.note.getText().toString();

                // this is jus a check
                if (isImageCropped) {
                    //do send cropped image
                } else if (uri != null) {
                    //do send uri
                } else if (bitmap != null) {
                    // do send camera image
                } else {
                    Toast.makeText(AddToClosetActivity.this, "No image found", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (category.equals("Category")) {
                    Toast.makeText(AddToClosetActivity.this, "Invalid category", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (season.equals("Season")) {
                    Toast.makeText(AddToClosetActivity.this, "Invalid season", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (note.isEmpty()) {
                    note = " ";
                }

                binding.proB.setVisibility(View.VISIBLE);
                binding.continueText.setVisibility(View.GONE);

                if (isImageCropped) {
                    //do send cropped image

                    Bitmap bitmap = donBitmap;

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

                    closetViewModel.uploadImageByte(byteArrayOutputStream.toByteArray(), category, season, note);

                } else if (uri != null) {
                    //do send uri

                    closetViewModel.uploadImageUri(uri, category, season, note);

                } else if (bitmap != null) {
                    // do send camera image

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    closetViewModel.uploadImageByte(byteArrayOutputStream.toByteArray(), category, season, note);
                }

            }
        });

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
                doImageCrop();

                break;
            case R.id.pick_image:

                PickImageBottomSheetFragment fragment = new PickImageBottomSheetFragment();
                fragment.show(getSupportFragmentManager(), "image");

                break;

        }

        return super.onOptionsItemSelected(item);


    }

    void doImageCrop() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this)
                .setTitle("Crop image")
                .setMessage("you need to pick an image first before trying to crop it")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = alertDialog.create();
        if (uri == null && bitmap == null) {
            dialog.show();
            return;
        }

        Intent intent = new Intent(this, CropActivity.class);
        if (uri == null) {
            //send bitmap

            intent.putExtra("bitmap", bitmap);

        } else {

            //send uri
            intent.putExtra("uri", uri);
        }
        startActivityForResult(intent, 4);

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

        if (requestCode == imageFileRequestCode && resultCode == RESULT_OK && data.getData() != null) {

            uri = data.getData();
            binding.imagePH.setVisibility(View.GONE);
            binding.maindImage.setImageURI(uri);

            bitmap = null;
            isImageCropped = false;
            donBitmap = null;

        }

        if (requestCode == 4 && resultCode == RESULT_OK) {

            donBitmap = (Bitmap) data.getParcelableExtra("doneBitmap");
            isImageCropped = true;
            binding.maindImage.setImageBitmap(donBitmap);


        }

        if (requestCode == imageCamera && resultCode == RESULT_OK && data.getExtras() != null) {

            Bundle bundle = data.getExtras();

            bitmap = (Bitmap) bundle.get("data");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

            binding.imagePH.setVisibility(View.GONE);
            binding.maindImage.setImageBitmap(bitmap);

            uri = null;
            isImageCropped = false;
            donBitmap = null;
        }

    }
}