package com.eflexsoft.easyclosest.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eflexsoft.easyclosest.ClosetItemDetailsActivity;
import com.eflexsoft.easyclosest.EventDetailsActivity;
import com.eflexsoft.easyclosest.FullClosetItemListActivity;
import com.eflexsoft.easyclosest.OutfitDatiledActivity;
import com.eflexsoft.easyclosest.R;
import com.eflexsoft.easyclosest.adapter.FavoritesAdapter;
import com.eflexsoft.easyclosest.databinding.EventItemBinding;
import com.eflexsoft.easyclosest.databinding.FragmentEventBinding;
import com.eflexsoft.easyclosest.databinding.SecondRecyclerViewItemLayoutBinding;
import com.eflexsoft.easyclosest.model.ClosetItem;
import com.eflexsoft.easyclosest.model.Event;
import com.eflexsoft.easyclosest.model.OutfitItem;
import com.eflexsoft.easyclosest.viewholder.ClosetItemViewHolder;
import com.eflexsoft.easyclosest.viewholder.EventItemViewHolder;
import com.eflexsoft.easyclosest.viewmodel.AddToClosetViewModel;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;


public class EventFragment extends Fragment {

    FragmentEventBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_event, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event, container, false);

        View view = binding.getRoot();

        binding.swipe.setRefreshing(true);

        binding.swipe.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecyclerView();
            }
        });

        initRecyclerView();

        return view;
    }

    void initRecyclerView() {

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        Query query = firestore.collection("Events").document(FirebaseAuth.getInstance().getUid())
                .collection("items").orderBy("id", Query.Direction.DESCENDING);

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPrefetchDistance(10)
                .setInitialLoadSizeHint(8)
                .setPageSize(30)
                .build();

        FirestorePagingOptions<Event> options = new FirestorePagingOptions.Builder<Event>()
                .setLifecycleOwner(this)
                .setQuery(query, config, Event.class)
                .build();

        FirestorePagingAdapter<Event, EventItemViewHolder> adapter = new FirestorePagingAdapter<Event, EventItemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull EventItemViewHolder holder, int position, @NonNull Event model) {

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getContext(), EventDetailsActivity.class);
                        intent.putExtra("id", model.getId());
                        intent.putExtra("note", model.getNote());
                        intent.putExtra("date", model.getDate());
                        intent.putExtra("imageUrl1", model.getImageUrl1());
                        intent.putExtra("imageUrl2", model.getImageUrl2());
                        intent.putExtra("imageUrl3", model.getImageUrl3());
                        intent.putExtra("imageUrl4", model.getImageUrl4());
                        intent.putExtra("imageUrl5", model.getImageUrl5());
                        intent.putExtra("imageUrl6", model.getImageUrl6());

                        startActivity(intent);
                    }
                });
                holder.binding.setEvent(model);

            }

            @NonNull
            @Override
            public EventItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

                EventItemBinding binding = DataBindingUtil.inflate(layoutInflater,
                        R.layout.event_item, parent, false);

                return new EventItemViewHolder(binding);
            }
        };


        binding.eventRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.eventRecyclerView.setAdapter(adapter);

        binding.swipe.setRefreshing(false);
    }
}