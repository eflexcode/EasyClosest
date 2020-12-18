package com.eflexsoft.easyclosest;

import androidx.annotation.NonNull;
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
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.eflexsoft.easyclosest.R;
import com.eflexsoft.easyclosest.adapter.SlideImageAdapter;
import com.eflexsoft.easyclosest.databinding.ActivityAddEventBinding;
import com.eflexsoft.easyclosest.fragment.PickDateFragment;
import com.eflexsoft.easyclosest.viewmodel.EventViewModel;
import com.kroegerama.imgpicker.BottomSheetImagePicker;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, BottomSheetImagePicker.OnImagesSelectedListener {

    ActivityAddEventBinding binding;
    List<Uri> uriList = new ArrayList<>();

    Intent intent;

    SlideImageAdapter slideImageAdapter;

    EventViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_event);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_event);

        viewModel = new ViewModelProvider(this).get(EventViewModel.class);

        setSupportActionBar(binding.toolb);

        intent = getIntent();

        binding.toolb.setNavigationIcon(R.drawable.ic_left_arrow2);
        binding.toolb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.outfit_menu2, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.pick_image_file:
                if (ActivityCompat.checkSelfPermission(AddEventActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED &&
                        ActivityCompat.checkSelfPermission(AddEventActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                ) {

                    ActivityCompat.requestPermissions(AddEventActivity.this,
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
            case R.id.post:

                post();

                break;
        }

        return true;

    }

    void post() {
        String note = binding.note.getText().toString();
        String date = binding.date.getText().toString();

        if (note.trim().isEmpty()) {
            Toast.makeText(AddEventActivity.this, "whats happening  is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (date.trim().isEmpty()) {
            Toast.makeText(AddEventActivity.this, "Please pick a date", Toast.LENGTH_SHORT).show();
            return;
        }

        if (uriList.size() == 0) {
            Toast.makeText(AddEventActivity.this, "Please add an image", Toast.LENGTH_SHORT).show();
            return;
        }

        viewModel.addToEvent(uriList, note, date);
        Toast.makeText(this, "Uploading", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String date = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());

        binding.date.setText(date);
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
}