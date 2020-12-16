package com.eflexsoft.easyclosest.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.eflexsoft.easyclosest.ClosetItemDetailsActivity;
import com.eflexsoft.easyclosest.R;
import com.eflexsoft.easyclosest.databinding.FullClosetItemLayoutBinding;
import com.eflexsoft.easyclosest.model.ClosetItem;

public class FavoritesAdapter extends ListAdapter<ClosetItem, FavoritesAdapter.ClosetItemViewHolder> {

    Context context;

    public FavoritesAdapter(Context context) {
        super(itemCallback);
        this.context = context;
    }

    static DiffUtil.ItemCallback<ClosetItem> itemCallback = new DiffUtil.ItemCallback<ClosetItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull ClosetItem oldItem, @NonNull ClosetItem newItem) {
            return oldItem.isFavourite() == newItem.isFavourite()
                    && oldItem.getCategory().equals(newItem.getCategory())
                    && oldItem.getId() == newItem.getId()
                    && oldItem.getImageUrl().equals(newItem.getImageUrl())
                    && oldItem.getNote().equals(newItem.getNote())
                    && oldItem.getSeason().equals(newItem.getSeason())
                    && oldItem.getUserId().equals(newItem.getUserId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull ClosetItem oldItem, @NonNull ClosetItem newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public ClosetItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        FullClosetItemLayoutBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.full_closet_item_layout, parent, false);

        return new FavoritesAdapter.ClosetItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ClosetItemViewHolder holder, int position) {


        ClosetItem item = getItem(position);

        if (item != null) {
            holder.binding.setItemData(item);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ClosetItemDetailsActivity.class);
                intent.putExtra("itemImageUrl", item.getImageUrl());
                intent.putExtra("category", item.getCategory());
                intent.putExtra("season", item.getSeason());
                intent.putExtra("note", item.getNote());
                intent.putExtra("id", item.getId());
                intent.putExtra("isFavorite", item.isFavourite());

                Pair<View, String> pair = Pair.create(holder.binding.img, ViewCompat.getTransitionName(holder.binding.img));

                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, pair);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    context.startActivity(intent, activityOptionsCompat.toBundle());
                } else {
                    context.startActivity(intent);
                }
            }
        });

        if (getCurrentList().size() - 1 == position) {

//            CardView.LayoutParams layoutParams = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            layoutParams.setMargins(10, 10, 20, 0);
//
//            holder.binding.c.setLayoutParams(layoutParams);
        }
    }

    class ClosetItemViewHolder extends RecyclerView.ViewHolder {

        public FullClosetItemLayoutBinding binding;

        public ClosetItemViewHolder(FullClosetItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

}
