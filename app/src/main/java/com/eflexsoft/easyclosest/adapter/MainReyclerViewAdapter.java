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
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.eflexsoft.easyclosest.ClosetItemDetailsActivity;
import com.eflexsoft.easyclosest.R;
import com.eflexsoft.easyclosest.databinding.FirstRecycleViewLayoutBinding;
import com.eflexsoft.easyclosest.databinding.SecondRecyclerViewItemLayoutBinding;
import com.eflexsoft.easyclosest.model.ClosetCategoryItem;
import com.eflexsoft.easyclosest.model.ClosetItem;
import com.eflexsoft.easyclosest.viewholder.ClosetItemViewHolder;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainReyclerViewAdapter extends ListAdapter<ClosetCategoryItem, MainReyclerViewAdapter.ViewHolder> {

    Context context;

    public MainReyclerViewAdapter(Context context) {
        super(callback);
        this.context = context;
    }

    static DiffUtil.ItemCallback<ClosetCategoryItem> callback = new DiffUtil.ItemCallback<ClosetCategoryItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull ClosetCategoryItem oldItem, @NonNull ClosetCategoryItem newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull ClosetCategoryItem oldItem, @NonNull ClosetCategoryItem newItem) {
            return oldItem.getCategoryName().equals(newItem.getCategoryName()) && oldItem.getCategorySize().equals(newItem.getCategorySize());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        FirstRecycleViewLayoutBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.first_recycle_view_layout, parent, false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ClosetCategoryItem closetCategoryItem = getItem(position);

        if (closetCategoryItem != null) {
            holder.binding.categoryCount.setText(closetCategoryItem.getCategorySize());
            holder.binding.categoryName.setText(closetCategoryItem.getCategoryName());

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);

            holder.binding.secondRecycler.setLayoutManager(layoutManager);

            setSecondRecyclerViewAdapter(holder, closetCategoryItem.getCategoryName());

        }

        if (getCurrentList().size() - 1 == position) {

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 850);
            layoutParams.setMargins(0, 0, 0, 10);

            holder.binding.m.setLayoutParams(layoutParams);
        }

    }

    private void setSecondRecyclerViewAdapter(ViewHolder holder, String categoryName) {

//        DocumentReference reference = firestore.collection("Closets").document(FirebaseAuth.getInstance().getUid())
//                .collection(category).document(id);

        Query query = FirebaseFirestore.getInstance().collection("Closets").document(FirebaseAuth.getInstance().getUid())
                .collection(categoryName).orderBy("id", Query.Direction.DESCENDING);

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setMaxSize(30)
                .setPageSize(10)
                .setPrefetchDistance(5)
                .setInitialLoadSizeHint(8)
                .build();


        FirestorePagingOptions<ClosetItem> pagingOptions = new FirestorePagingOptions.Builder<ClosetItem>()
                .setLifecycleOwner((LifecycleOwner) context)
                .setQuery(query, config, ClosetItem.class)
                .build();

        FirestorePagingAdapter<ClosetItem, ClosetItemViewHolder> holderFirestorePagingAdapter = new FirestorePagingAdapter<ClosetItem, ClosetItemViewHolder>(pagingOptions) {
            @Override
            protected void onBindViewHolder(@NonNull ClosetItemViewHolder holder, int position, @NonNull ClosetItem model) {

                holder.binding.setItemData(model);
                if (position == getCurrentList().size() - 1) {
                    holder.binding.fab.setVisibility(View.VISIBLE);
                } else {
                    holder.binding.fab.setVisibility(View.GONE);
                }

                holder.binding.itemImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, ClosetItemDetailsActivity.class);
                        intent.putExtra("itemImageUrl", model.getImageUrl());

                        Pair<View, String> viewStringPair = Pair.create(holder.binding.itemImage, ViewCompat.getTransitionName(holder.binding.itemImage));

                        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                (Activity) context, viewStringPair
                        );

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            context.startActivity(intent, activityOptionsCompat.toBundle());
                        } else {
                            context.startActivity(intent);
                        }

                    }
                });

            }

            @NonNull
            @Override
            public ClosetItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

                SecondRecyclerViewItemLayoutBinding binding = DataBindingUtil.inflate(layoutInflater,
                        R.layout.second_recycler_view_item_layout, parent, false);

                return new ClosetItemViewHolder(binding);
            }
        };

        holder.binding.secondRecycler.setAdapter(holderFirestorePagingAdapter);

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        FirstRecycleViewLayoutBinding binding;

        public ViewHolder(FirstRecycleViewLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

        }
    }

}
