package com.eflexsoft.easyclosest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.eflexsoft.easyclosest.databinding.ActivityChangeOutfitTextBinding;
import com.eflexsoft.easyclosest.fragment.PickDateFragment;
import com.eflexsoft.easyclosest.viewmodel.ChangeOutfitTextViewModel;
import com.kroegerama.imgpicker.BottomSheetImagePicker;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ChangeOutfitTextActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Intent intent;
    ActivityChangeOutfitTextBinding binding;
    ChangeOutfitTextViewModel viewModel;

    String season;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_change_outfit_text);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_outfit_text);

        viewModel = new ViewModelProvider(this).get(ChangeOutfitTextViewModel.class);

        intent = getIntent();

        season = intent.getStringExtra("season");
        String note = intent.getStringExtra("note");
        String date = intent.getStringExtra("date");
        long id = intent.getLongExtra("id", 0);


        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.weather, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.weatherSpinner.setAdapter(arrayAdapter);
        binding.note.setText(note);
        binding.date.setText(date);

        binding.weatherSpinner.setSelection(setSpinnerItem(binding.weatherSpinner, season));

        binding.weatherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                season = arrayAdapter.getItem(position).toString();
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
            }
        });

        binding.loginContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String note = binding.note.getText().toString();
                String date = binding.date.getText().toString();

                if (season.equals("Season")) {
                    Toast.makeText(ChangeOutfitTextActivity.this, "Invalid season", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (note.trim().isEmpty()) {
                    Toast.makeText(ChangeOutfitTextActivity.this, "Note is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (date.trim().isEmpty()) {
                    Toast.makeText(ChangeOutfitTextActivity.this, "Please pick a date", Toast.LENGTH_SHORT).show();
                    return;
                }

                binding.proB.setVisibility(View.VISIBLE);
                binding.continueText.setVisibility(View.GONE);

                viewModel.UpdateTet(String.valueOf(id), note, date, season);

            }
        });

        viewModel.getIsUploadInSuccessful().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {
                    finish();
                } else {
                    binding.proB.setVisibility(View.GONE);
                    binding.continueText.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    public int setSpinnerItem(Spinner spinner, String s) {

        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {

            if (spinner.getItemAtPosition(i).equals(s)) {
                index = i;
            }

        }
        return index;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.outfit_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.pick_date) {
            PickDateFragment pickDateFragment = new PickDateFragment();
            pickDateFragment.show(getSupportFragmentManager(), "pick");
        }

        return true;

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