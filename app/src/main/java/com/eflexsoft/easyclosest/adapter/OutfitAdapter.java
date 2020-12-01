package com.eflexsoft.easyclosest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.eflexsoft.easyclosest.R;
import com.eflexsoft.easyclosest.databinding.FirstRecycleViewLayoutBinding;
import com.eflexsoft.easyclosest.databinding.OutfitItemBinding;
import com.eflexsoft.easyclosest.model.OutfitItem;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;

public class OutfitAdapter extends FirestorePagingAdapter<OutfitItem,OutfitAdapter.ViewHolder> {

    /**
     * Construct a new FirestorePagingAdapter from the given {@link FirestorePagingOptions}.
     *
     * @param options
     */
    public OutfitAdapter(@NonNull FirestorePagingOptions<OutfitItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull OutfitItem model) {

        holder.binding.setOutfitItem(model);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        OutfitItemBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.outfit_item, parent, false);

        return new ViewHolder(binding);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        OutfitItemBinding binding;

        public ViewHolder(@NonNull OutfitItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }

}
