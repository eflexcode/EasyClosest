package com.eflexsoft.easyclosest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.eflexsoft.easyclosest.R;
import com.eflexsoft.easyclosest.databinding.ImageItemBinding;
import com.eflexsoft.easyclosest.databinding.SlideImageItem2Binding;
import com.eflexsoft.easyclosest.model.ImageItem2;
import com.github.islamkhsh.CardSliderAdapter;

import java.util.List;

public class ImageSlideCardAdapter extends CardSliderAdapter<ImageSlideCardAdapter.ViewHolder> {

    List<ImageItem2> imageItem2s;
    Context context;

    public ImageSlideCardAdapter(List<ImageItem2> imageItem2s,Context context) {
        this.imageItem2s = imageItem2s;
        this.context = context;
    }

    @Override
    public void bindVH(@NonNull ViewHolder viewHolder, int i) {

        ImageItem2 imageItem2 = imageItem2s.get(i);

        viewHolder.binding.setItem(imageItem2);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, imageItem2.getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SlideImageItem2Binding binding = DataBindingUtil.inflate(inflater, R.layout.slide_image_item2,parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        return imageItem2s.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        SlideImageItem2Binding binding;

        public ViewHolder(@NonNull SlideImageItem2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
