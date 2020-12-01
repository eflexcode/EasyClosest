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
import androidx.recyclerview.widget.RecyclerView;

import com.eflexsoft.easyclosest.ClosetItemDetailsActivity;
import com.eflexsoft.easyclosest.R;
import com.eflexsoft.easyclosest.databinding.FirstRecycleViewLayoutBinding;
import com.eflexsoft.easyclosest.databinding.OutfitItemBinding;
import com.eflexsoft.easyclosest.model.OutfitItem;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;

public class OutfitAdapter extends FirestorePagingAdapter<OutfitItem, OutfitAdapter.ViewHolder> {

    /**
     * Construct a new FirestorePagingAdapter from the given {@link FirestorePagingOptions}.
     *
     * @param options
     */

    Context context;

    public OutfitAdapter(@NonNull FirestorePagingOptions<OutfitItem> options, Context context) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull OutfitItem model) {

        holder.binding.setOutfitItem(model);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ClosetItemDetailsActivity.class);
                intent.putExtra("id", model.getId());
                intent.putExtra("season", model.getSeason());
                intent.putExtra("note", model.getNote());
                intent.putExtra("date", model.getDate());
                intent.putExtra("imageUrl1", model.getImageUrl1());
                intent.putExtra("imageUrl2", model.getImageUrl2());
                intent.putExtra("imageUrl3", model.getImageUrl3());
                intent.putExtra("imageUrl4", model.getImageUrl4());
                intent.putExtra("imageUrl5", model.getImageUrl5());
                intent.putExtra("imageUrl6", model.getImageUrl6());

                context.startActivity(intent);

            }
        });

//        if (getCurrentList().size()-1 == position){
//            CardView.LayoutParams layoutParams = new CardView.LayoutParams(CardView.LayoutParams.WRAP_CONTENT,850);
//            layoutParams.setMargins(5,10,5,30);
//
//            holder.binding.card.setLayoutParams(layoutParams);
//        }

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
