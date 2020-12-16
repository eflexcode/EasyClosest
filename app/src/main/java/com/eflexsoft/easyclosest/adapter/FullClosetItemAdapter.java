package com.eflexsoft.easyclosest.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.eflexsoft.easyclosest.ClosetItemDetailsActivity;
import com.eflexsoft.easyclosest.OutfitDatiledActivity;
import com.eflexsoft.easyclosest.R;
import com.eflexsoft.easyclosest.databinding.FullClosetItemLayoutBinding;
import com.eflexsoft.easyclosest.databinding.OutfitItemBinding;
import com.eflexsoft.easyclosest.model.ClosetItem;
import com.eflexsoft.easyclosest.model.OutfitItem;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;

public class FullClosetItemAdapter extends FirestorePagingAdapter<ClosetItem, FullClosetItemAdapter.ViewHolder> {

    /**
     * Construct a new FirestorePagingAdapter from the given {@link FirestorePagingOptions}.
     *
     * @param options
     */

    Context context;

    public FullClosetItemAdapter(@NonNull FirestorePagingOptions<ClosetItem> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ClosetItem model) {

        holder.binding.setItemData(model);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ClosetItemDetailsActivity.class);
                intent.putExtra("itemImageUrl", model.getImageUrl());
                intent.putExtra("category", model.getCategory());
                intent.putExtra("season", model.getSeason());
                intent.putExtra("note", model.getNote());
                intent.putExtra("id", model.getId());
                intent.putExtra("isFavorite", model.isFavourite());

                Pair<View,String> pair = Pair.create(holder.binding.img, ViewCompat.getTransitionName(holder.binding.img));

                ActivityOptionsCompat activityOptionsCompat =  ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,pair);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                    context.startActivity(intent,activityOptionsCompat.toBundle());
                }else {
                    context.startActivity(intent);
                }
            }
        });



    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        FullClosetItemLayoutBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.full_closet_item_layout, parent, false);

        return new ViewHolder(binding);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        FullClosetItemLayoutBinding binding;

        public ViewHolder(@NonNull FullClosetItemLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }

}
