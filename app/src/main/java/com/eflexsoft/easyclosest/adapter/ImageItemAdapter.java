package com.eflexsoft.easyclosest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.eflexsoft.easyclosest.R;
import com.eflexsoft.easyclosest.databinding.ImageItemBinding;
import com.eflexsoft.easyclosest.model.ImageItem;

import java.util.List;

public class ImageItemAdapter extends RecyclerView.Adapter<ImageItemAdapter.ViewHolder> {

    List<ImageItem> imageItems;
    Context context;

    public ImageItemAdapter(List<ImageItem> imageItems, Context context) {
        this.imageItems = imageItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        ImageItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.image_item, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.setUrl(imageItems.get(position));

    }

    @Override
    public int getItemCount() {
        return imageItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageItemBinding binding;

        public ViewHolder(@NonNull ImageItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
