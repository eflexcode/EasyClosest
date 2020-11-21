package com.eflexsoft.easyclosest.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eflexsoft.easyclosest.R;
import com.eflexsoft.easyclosest.adapter.MainReyclerViewAdapter;
import com.eflexsoft.easyclosest.databinding.FragmentAllClothsBinding;
import com.eflexsoft.easyclosest.model.CategoryCount;
import com.eflexsoft.easyclosest.model.ClosetCategoryItem;
import com.eflexsoft.easyclosest.viewmodel.AllClothsViewModel;

import java.util.ArrayList;
import java.util.List;


public class AllClothsFragment extends Fragment {

    AllClothsViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        viewModel = new ViewModelProvider(getActivity()).get(AllClothsViewModel.class);

        FragmentAllClothsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_cloths, container, false);

        View view = binding.getRoot();

        binding.mainRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        MainReyclerViewAdapter adapter = new MainReyclerViewAdapter(getContext());
        binding.mainRecycler.setAdapter(adapter);

        List<ClosetCategoryItem> closetCategoryItems = new ArrayList<>();


        viewModel.getCategoryCountMutableLiveData().observe(getViewLifecycleOwner(), new Observer<CategoryCount>() {
            @Override
            public void onChanged(CategoryCount categoryCount) {

                closetCategoryItems.add(new ClosetCategoryItem("Shirts","("+categoryCount.getAccessories()+")"));

                adapter.submitList(closetCategoryItems);
            }
        });

        return view;
    }
}