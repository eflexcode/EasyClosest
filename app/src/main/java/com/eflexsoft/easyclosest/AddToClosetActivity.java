package com.eflexsoft.easyclosest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.eflexsoft.easyclosest.databinding.ActivityAddToClosetBinding;

public class AddToClosetActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_to_closet);



        ActivityAddToClosetBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_add_to_closet);


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
        menuInflater.inflate(R.menu.closet_menu,menu);

        return true;
    }
}