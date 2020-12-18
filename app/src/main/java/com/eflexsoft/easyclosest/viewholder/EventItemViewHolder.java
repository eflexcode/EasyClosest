package com.eflexsoft.easyclosest.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import com.eflexsoft.easyclosest.databinding.EventItemBinding;
import com.eflexsoft.easyclosest.databinding.SecondRecyclerViewItemLayoutBinding;

public class EventItemViewHolder extends RecyclerView.ViewHolder {

    public EventItemBinding binding;

    public EventItemViewHolder(EventItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}
