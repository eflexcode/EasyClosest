package com.eflexsoft.easyclosest.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.eflexsoft.easyclosest.R;
import com.eflexsoft.easyclosest.databinding.SlideImageItemBinding;
import com.eflexsoft.easyclosest.model.SlideImageItem;

import java.util.List;

public class SlideImageAdapter extends RecyclerView.Adapter<SlideImageAdapter.ViewHolder> {

    List<Uri> uriList;
    ViewPager2 viewPager2;

    public SlideImageAdapter(List<Uri> uriList, ViewPager2 viewPager2) {
        this.uriList = uriList;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        SlideImageItemBinding slideImageItemBinding = DataBindingUtil.inflate(inflater,
                R.layout.slide_image_item, parent, false);

        return new ViewHolder(slideImageItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Uri uri = uriList.get(position);

        if (uri != null){
            holder.binding.imageSlide.setImageURI(uri);
        }


    }

    @Override
    public int getItemCount() {
        return uriList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        SlideImageItemBinding binding;

        public ViewHolder(SlideImageItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

        }
    }

}
