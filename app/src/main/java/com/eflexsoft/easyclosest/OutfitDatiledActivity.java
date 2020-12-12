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

import com.eflexsoft.easyclosest.adapter.ImageItemAdapter;
import com.eflexsoft.easyclosest.adapter.ImageSlideCardAdapter;
import com.eflexsoft.easyclosest.databinding.ActivityAddToDailyOutfitBinding;
import com.eflexsoft.easyclosest.databinding.ActivityOutfitDatiledBinding;
import com.eflexsoft.easyclosest.fragment.DeleteBottomSheetFragment;
import com.eflexsoft.easyclosest.fragment.EditBottomSheetFragment;
import com.eflexsoft.easyclosest.fragment.PickImageBottomSheetFragment;
import com.eflexsoft.easyclosest.model.ImageItem;
import com.eflexsoft.easyclosest.model.ImageItem2;
import com.eflexsoft.easyclosest.model.UpdateImage;
import com.eflexsoft.easyclosest.viewmodel.AddToDailyOutfitViewModel;
import com.eflexsoft.easyclosest.viewmodel.ChangeOutfitImageViewModel;
import com.eflexsoft.easyclosest.viewmodel.ClosetItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class OutfitDatiledActivity extends AppCompatActivity {

    Intent intent;
    ClosetItemViewModel viewModel;
    AddToDailyOutfitViewModel outfitViewModel;
    ChangeOutfitImageViewModel imageViewModel;
    String note;
    String season;
    String date;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_outfit_datiled);

        ActivityOutfitDatiledBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_outfit_datiled);
        viewModel = new ViewModelProvider(this).get(ClosetItemViewModel.class);
        outfitViewModel = new ViewModelProvider(this).get(AddToDailyOutfitViewModel.class);
        imageViewModel = new ViewModelProvider(this).get(ChangeOutfitImageViewModel.class);

        intent = getIntent();
        setSupportActionBar(binding.toolb);
        id = intent.getLongExtra("id", 0);
        note = intent.getStringExtra("note");
        season = intent.getStringExtra("season");
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

        ImageSlideCardAdapter cardAdapter = new ImageSlideCardAdapter(imageItems, this);
        binding.viewPager.setAdapter(cardAdapter);
        binding.toolb.setNavigationIcon(R.drawable.ic_left_arrow2);
        binding.toolb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.date.setText(date);
        binding.season.setText(season);

        if (!note.trim().isEmpty()) {
            binding.note.setText(note);
        } else {
            binding.note.setText("No note was added");
        }

        viewModel.getIsDelete().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {

                    outfitViewModel.delete(String.valueOf(id));
                    Toast.makeText(OutfitDatiledActivity.this, "Deleting", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                }

            }
        });
        imageViewModel.updateImageLiveData().observe(this, new Observer<UpdateImage>() {
            @Override
            public void onChanged(UpdateImage updateImage) {
                imageItems.add(updateImage.getPosition(), new ImageItem2(updateImage.getUrl(), updateImage.getName(), updateImage.getId()));
                cardAdapter.setImageItem2s(imageItems, updateImage.getPosition());
                Toast.makeText(OutfitDatiledActivity.this, "svvvvvvvvvvvvvvvvv", Toast.LENGTH_SHORT).show();
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
//
//            case R.id.delete:
//
//                DeleteBottomSheetFragment fragment = new DeleteBottomSheetFragment();
//                fragment.show(getSupportFragmentManager(), "delete");
//
//                break;
            case R.id.edit:

//                Intent intent = new Intent(this, UpdateClosetItemActivity.class);
//                intent.putExtra("itemImageUrl", itemImageUrl);
//                intent.putExtra("category", category);
//                intent.putExtra("season", season);
//                intent.putExtra("note", note);
//                intent.putExtra("id", id);
//                intent.putExtra("isFavorite", isFavorite);
//
//                startActivity(intent);

//                EditBottomSheetFragment fragment2 = new EditBottomSheetFragment();
//                fragment2.show(getSupportFragmentManager(), "edit");

                Intent intent = new Intent(this, ChangeOutfitTextActivity.class);
                intent.putExtra("season", season);
                intent.putExtra("note", note);
                intent.putExtra("id", id);
                startActivity(intent);
                break;

        }

        return super.onOptionsItemSelected(item);


    }

}