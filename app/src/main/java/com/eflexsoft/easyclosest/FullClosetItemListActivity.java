package com.eflexsoft.easyclosest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eflexsoft.easyclosest.R;
import com.eflexsoft.easyclosest.adapter.FullClosetItemAdapter;
import com.eflexsoft.easyclosest.databinding.ActivityFullClosetItemListBinding;
import com.eflexsoft.easyclosest.model.ClosetItem;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class FullClosetItemListActivity extends AppCompatActivity {

    String category;
    Intent intent;
    ActivityFullClosetItemListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_full_closet_item_list);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_full_closet_item_list);
        intent = getIntent();

        category = intent.getStringExtra("category");

        binding.toolb.setTitle(category);

        binding.swipe.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.swipe.setRefreshing(true);
        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecyclerView();
            }
        });

        binding.toolb.setNavigationIcon(R.drawable.ic_left_arrow2);
        binding.toolb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initRecyclerView();
    }

    void initRecyclerView() {

        Query query = FirebaseFirestore.getInstance().collection("Closets").document(FirebaseAuth.getInstance().getUid())
                .collection(category).orderBy("id", Query.Direction.DESCENDING);

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(10)
                .setEnablePlaceholders(false)
                .setMaxSize(30)
                .setPrefetchDistance(8)
                .setInitialLoadSizeHint(5).build();

        FirestorePagingOptions<ClosetItem> firestorePagingOptions = new FirestorePagingOptions.Builder<ClosetItem>()
                .setLifecycleOwner(this)
                .setQuery(query, config, ClosetItem.class)
                .build();

        FullClosetItemAdapter adapter = new FullClosetItemAdapter(firestorePagingOptions, this);
//        binding.allClosets.setHasFixedSize(true);
        binding.allClosets.setLayoutManager(new LinearLayoutManager(this));
        binding.allClosets.setAdapter(adapter);

        binding.swipe.setRefreshing(false);
    }
}