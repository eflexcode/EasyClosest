package com.eflexsoft.easyclosest.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eflexsoft.easyclosest.R;
import com.eflexsoft.easyclosest.adapter.OutfitAdapter;
import com.eflexsoft.easyclosest.databinding.FragmentDailyClothsBinding;
import com.eflexsoft.easyclosest.model.OutfitItem;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class DailyClothsFragment extends Fragment {
    FragmentDailyClothsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_daily_cloths, container, false);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_daily_cloths, container, false);

        View view = binding.getRoot();

        binding.swipe.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecyclerView();

            }
        });

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, RecyclerView.HORIZONTAL);

        binding.outfitRecyclerView.setLayoutManager(layoutManager);

        return view;

    }

    public void initRecyclerView() {

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        Query query = firestore.collection("Outfit").document(FirebaseAuth.getInstance().getUid())
                .collection("items").orderBy("id", Query.Direction.DESCENDING);

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPrefetchDistance(30)
                .setPageSize(20)
                .setInitialLoadSizeHint(10)
                .setMaxSize(30)
                .build();

        FirestorePagingOptions<OutfitItem> options = new FirestorePagingOptions.Builder<OutfitItem>()
                .setLifecycleOwner(this)
                .setQuery(query, config, OutfitItem.class)
                .build();

        OutfitAdapter adapter = new OutfitAdapter(options);
        binding.outfitRecyclerView.setAdapter(adapter);

    }
}