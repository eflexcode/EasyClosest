package com.eflexsoft.easyclosest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.eflexsoft.easyclosest.R.id;
import com.eflexsoft.easyclosest.databinding.ActivityEditEventBinding;
import com.eflexsoft.easyclosest.fragment.DeleteBottomSheetFragment;
import com.eflexsoft.easyclosest.fragment.PickDateFragment;
import com.eflexsoft.easyclosest.viewmodel.EventViewModel;

import java.text.DateFormat;
import java.util.Calendar;

public class EditEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ActivityEditEventBinding binding;
    Intent intent;
    String note;
    String date;
    long id;
    EventViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_event);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_event);
        viewModel = new ViewModelProvider(this).get(EventViewModel.class);
        intent = getIntent();
        id = intent.getLongExtra("id", 0);
        note = intent.getStringExtra("note");
        date = intent.getStringExtra("date");

        binding.date.setText(date);
        binding.note.setText(note);
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
        menuInflater.inflate(R.menu.event_update_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.post:
                post();
                break;

            case R.id.pick_date:

                PickDateFragment pickDateFragment = new PickDateFragment();
                pickDateFragment.show(getSupportFragmentManager(), "pick");

                break;

        }
        return super.onOptionsItemSelected(item);

    }

    void post() {
        String note = binding.note.getText().toString();
        String date = binding.date.getText().toString();

        if (note.trim().isEmpty()) {
            Toast.makeText(EditEventActivity.this, "Whats happening  is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (date.trim().isEmpty()) {
            Toast.makeText(EditEventActivity.this, "Please pick a date", Toast.LENGTH_SHORT).show();
            return;
        }
        viewModel.UpdateTet(String.valueOf(id), note, date);
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
}