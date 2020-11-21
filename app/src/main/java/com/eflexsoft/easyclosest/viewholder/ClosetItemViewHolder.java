package com.eflexsoft.easyclosest.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eflexsoft.easyclosest.databinding.SecondRecyclerViewItemLayoutBinding;

public class ClosetItemViewHolder extends RecyclerView.ViewHolder {

    public SecondRecyclerViewItemLayoutBinding binding;

    public ClosetItemViewHolder(SecondRecyclerViewItemLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}
