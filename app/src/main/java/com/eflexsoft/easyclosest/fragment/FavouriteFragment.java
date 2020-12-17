package com.eflexsoft.easyclosest.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eflexsoft.easyclosest.R;
import com.eflexsoft.easyclosest.adapter.FavoritesAdapter;
import com.eflexsoft.easyclosest.adapter.FullClosetItemAdapter;
import com.eflexsoft.easyclosest.databinding.FragmentFavuoriteBinding;
import com.eflexsoft.easyclosest.model.ClosetItem;
import com.eflexsoft.easyclosest.viewmodel.FavoritesViewModel;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class FavouriteFragment extends Fragment {

    FragmentFavuoriteBinding binding;
    FavoritesViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favuorite, container, false);

        View view = binding.getRoot();

//        return inflater.inflate(R.layout.fragment_favuorite, container, false);'

        viewModel = new ViewModelProvider(getActivity()).get(FavoritesViewModel.class);

        binding.swipe.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.swipe.setRefreshing(true);
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

//        Query query = FirebaseFirestore.getInstance().collectionGroup("Closets").whereEqualTo("userId",FirebaseAuth.getInstance().getUid());
//        Query query1 = FirebaseFirestore.getInstance().collection("").document().
//
//                ;
//
//        Query query = FirebaseFirestore.getInstance().collection("Closets").document(FirebaseAuth.getInstance().getUid())
//                .collection("Underwear").orderBy("id", Query.Direction.DESCENDING);

//        PagedList.Config config = new PagedList.Config.Builder()
//                .setEnablePlaceholders(false)
//                .setMaxSize(30)
//                .setPageSize(10)
//                .setPrefetchDistance(5)
//                .setInitialLoadSizeHint(8)
//                .build();
//
//        FirestorePagingOptions<ClosetItem> pagingOptions = new FirestorePagingOptions.Builder<ClosetItem>()
//                .setLifecycleOwner(getActivity())
//                .setQuery(query, config, ClosetItem.class)
//                .build();
//
//        FullClosetItemAdapter adapter = new FullClosetItemAdapter(pagingOptions, getContext());

        FavoritesAdapter adapter = new FavoritesAdapter(getContext());

        binding.favouriteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.favouriteRecyclerView.setAdapter(adapter);

        viewModel.closetItemLiveData().observe(getActivity(), new Observer<List<ClosetItem>>() {
            @Override
            public void onChanged(List<ClosetItem> closetItems) {

                if (closetItems.isEmpty()) {
                    binding.sorry.setVisibility(View.VISIBLE);
                } else {
                    binding.sorry.setVisibility(View.GONE);
                }

                adapter.submitList(closetItems);
            }
        });
        binding.swipe.setRefreshing(false);
    }
}