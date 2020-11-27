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
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eflexsoft.easyclosest.databinding.ActivityUpdateClosetItemBinding;
import com.eflexsoft.easyclosest.fragment.PickImageBottomSheetFragment;
import com.eflexsoft.easyclosest.viewmodel.PickImageViewModel;
import com.eflexsoft.easyclosest.viewmodel.UpdateClosetItemViewModel;

import java.io.ByteArrayOutputStream;

public class UpdateClosetItemActivity extends AppCompatActivity {

    Bitmap bitmap;
    Bitmap donBitmap;
    boolean isImageCropped;
    boolean isImageChanged;

//    String category;
//    String season;

    PickImageViewModel viewModel;
    int imageFileRequestCode = 7;
    int imageCamera = 9;
    Uri uri;
    ActivityUpdateClosetItemBinding binding;

    Intent intent;

    String category;
    String season;
    String note;
    String itemImageUrl;
    String id;
    long longId;
    boolean isFavorite;

    UpdateClosetItemViewModel closetItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_update_closet_item);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_closet_item);

        setSupportActionBar(binding.toolb);

        intent = getIntent();
        category = intent.getStringExtra("category");
        season = intent.getStringExtra("season");
        note = intent.getStringExtra("note");
        id = String.valueOf(intent.getLongExtra("id", 0));
        itemImageUrl = intent.getStringExtra("itemImageUrl");
        isFavorite = intent.getBooleanExtra("isFavorite", false);

        longId = intent.getLongExtra("id", 0);

        binding.note.setText(note);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.color.brown);
        requestOptions.error(R.color.brown);

        Glide.with(this).load(itemImageUrl).apply(requestOptions).into(binding.maindImage);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_array, android.R.layout.simple_spinner_item);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> arrayAdapterSeason = ArrayAdapter.createFromResource(this, R.array.weather, android.R.layout.simple_spinner_item);

        arrayAdapterSeason.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//        binding.categorySpinner.setAdapter(arrayAdapter);
//        binding.categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                category = arrayAdapter.getItem(position).toString();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

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

//        binding.categorySpinner.setSelection(sinnerIndex(binding.categorySpinner, category));
        binding.weatherSpinner.setSelection(sinnerIndex(binding.weatherSpinner, season));

        viewModel = new ViewModelProvider(this).get(PickImageViewModel.class);
        viewModel.getIsCamera().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {

                    if (ActivityCompat.checkSelfPermission(UpdateClosetItemActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(UpdateClosetItemActivity.this, new String[]{Manifest.permission.CAMERA}, 3);
                    } else {
                        pickImageCamera();
                    }

                } else {

                    if (ActivityCompat.checkSelfPermission(UpdateClosetItemActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED &&
                            ActivityCompat.checkSelfPermission(UpdateClosetItemActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                    ) {

                        ActivityCompat.requestPermissions(UpdateClosetItemActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);

                    } else {
                        pickImageFile();
                    }

                }

            }
        });

        closetItemViewModel = new ViewModelProvider(this).get(UpdateClosetItemViewModel.class);

        binding.loginContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = binding.note.getText().toString();
                if (season.equals("Season")) {
                    Toast.makeText(UpdateClosetItemActivity.this, "Invalid season", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (note.isEmpty()) {
                    note = " ";
                }


                if (!isImageChanged) {
                    // send gotten url
                    closetItemViewModel.updateWithStringImage(longId, category, season, note);
                } else if (isImageCropped) {
                    //send cropped image
                    Bitmap bitmap = donBitmap;

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

                    closetItemViewModel.uploadImageByte(longId,
                            byteArrayOutputStream.toByteArray(), category, season, note);

                } else if (bitmap != null) {
                    //send camera image
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    closetItemViewModel.uploadImageByte(longId,
                            byteArrayOutputStream.toByteArray(), category, season, note);
                } else if (uri != null) {
                    //send image uri
                    closetItemViewModel.uploadImageUri(uri, category, season, note, longId);
                } else {
                    Toast.makeText(UpdateClosetItemActivity.this, "No image found", Toast.LENGTH_SHORT).show();
                    return;
                }

                binding.proB.setVisibility(View.VISIBLE);
                binding.continueText.setVisibility(View.GONE);

            }
        });

        closetItemViewModel.getIsUpdateSuccessful().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {

                    Toast.makeText(UpdateClosetItemActivity.this, "upload successful", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    binding.proB.setVisibility(View.GONE);
                    binding.continueText.setVisibility(View.VISIBLE);

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
//            binding.imagePH.setVisibility(View.GONE);
            binding.maindImage.setImageURI(uri);

            bitmap = null;
            isImageCropped = false;
            donBitmap = null;
            isImageChanged = true;

        }

        if (requestCode == 4 && resultCode == RESULT_OK) {

            donBitmap = (Bitmap) data.getParcelableExtra("doneBitmap");
            isImageCropped = true;
            isImageChanged = true;
            binding.maindImage.setImageBitmap(donBitmap);


        }

        if (requestCode == imageCamera && resultCode == RESULT_OK && data.getExtras() != null) {

            Bundle bundle = data.getExtras();

            bitmap = (Bitmap) bundle.get("data");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

//            binding.imagePH.setVisibility(View.GONE);
            binding.maindImage.setImageBitmap(bitmap);

            uri = null;
            isImageCropped = false;
            donBitmap = null;
            isImageChanged = true;
        }

    }

    public int sinnerIndex(Spinner spinner, String s) {

        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {

            if (spinner.getItemAtPosition(i).equals(s)) {
                index = i;
            }

        }
        return index;
    }

}