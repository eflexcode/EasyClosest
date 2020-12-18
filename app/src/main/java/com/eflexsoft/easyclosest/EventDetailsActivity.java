package com.eflexsoft.easyclosest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.eflexsoft.easyclosest.adapter.ImageSlideCardAdapter;
import com.eflexsoft.easyclosest.databinding.ActivityEventDetailsBinding;
import com.eflexsoft.easyclosest.fragment.DeleteBottomSheetFragment;
import com.eflexsoft.easyclosest.model.ImageItem2;
import com.eflexsoft.easyclosest.viewmodel.ClosetItemViewModel;
import com.eflexsoft.easyclosest.viewmodel.EventViewModel;

import java.util.ArrayList;
import java.util.List;

public class EventDetailsActivity extends AppCompatActivity {

    ActivityEventDetailsBinding binding;
    String note;
    String date;
    long id;

    Intent intent;
    ClosetItemViewModel viewModel;
    EventViewModel eventViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_event_details);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_event_details);
        viewModel = new ViewModelProvider(this).get(ClosetItemViewModel.class);
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        intent = getIntent();
        setSupportActionBar(binding.toolb);
        id = intent.getLongExtra("id", 0);
        note = intent.getStringExtra("note");
        date = intent.getStringExtra("date");
        String imageUrl1 = intent.getStringExtra("imageUrl1");
        String imageUrl2 = intent.getStringExtra("imageUrl2");
        String imageUrl3 = intent.getStringExtra("imageUrl3");
        String imageUrl4 = intent.getStringExtra("imageUrl4");
        String imageUrl5 = intent.getStringExtra("imageUrl5");
        String imageUrl6 = intent.getStringExtra("imageUrl6");

        List<ImageItem2> imageItems = new ArrayList<>();
        imageItems.add(new ImageItem2(imageUrl1, "imageUrl1", id));
        imageItems.add(new ImageItem2(imageUrl2, "imageUrl2", id));
        imageItems.add(new ImageItem2(imageUrl3, "imageUrl3", id));
        imageItems.add(new ImageItem2(imageUrl4, "imageUrl4", id));

        if (imageUrl5 != null) {
            imageItems.add(new ImageItem2(imageUrl5, "imageUrl5", id));
        }
        if (imageUrl6 != null) {
            imageItems.add(new ImageItem2(imageUrl6, "imageUrl6", id));
        }

        ImageSlideCardAdapter cardAdapter = new ImageSlideCardAdapter(imageItems, this,true);
        binding.viewPager.setAdapter(cardAdapter);
        binding.toolb.setNavigationIcon(R.drawable.ic_left_arrow2);
        binding.toolb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.date.setText(date);

        if (!note.trim().isEmpty()) {
            binding.note.setText(note);
        } else {
            binding.note.setText("No note was added");
        }

        viewModel.getIsDelete().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {

                    eventViewModel.delete(String.valueOf(id));
                    Toast.makeText(EventDetailsActivity.this, "Deleting", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.closet_detail_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.delete:

                DeleteBottomSheetFragment fragment = new DeleteBottomSheetFragment();
                fragment.show(getSupportFragmentManager(), "delete");

                break;
            case R.id.edit:

                Intent intent = new Intent(this, EditEventActivity.class);
                intent.putExtra("note", note);
                intent.putExtra("id", id);
                intent.putExtra("date",date);
                startActivity(intent);
                break;

        }

        return super.onOptionsItemSelected(item);

    }

}