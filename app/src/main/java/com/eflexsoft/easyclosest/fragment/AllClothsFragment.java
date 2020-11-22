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

                if (categoryCount != null) {

                    closetCategoryItems.add(new ClosetCategoryItem("Shirts", "(" + categoryCount.getShirts() + ")"));
                    closetCategoryItems.add(new ClosetCategoryItem("Trousers", "(" + categoryCount.getTrousers() + ")"));
                    closetCategoryItems.add(new ClosetCategoryItem("Harts", "(" + categoryCount.getHarts() + ")"));
                    closetCategoryItems.add(new ClosetCategoryItem("Caps", "(" + categoryCount.getCaps() + ")"));
                    closetCategoryItems.add(new ClosetCategoryItem("Head warmers", "(" + categoryCount.getHeadwarmer() + ")"));
                    closetCategoryItems.add(new ClosetCategoryItem("Belts", "(" + categoryCount.getBelts() + ")"));
                    closetCategoryItems.add(new ClosetCategoryItem("Coats", "(" + categoryCount.getCoats() + ")"));
                    closetCategoryItems.add(new ClosetCategoryItem("Sweaters", "(" + categoryCount.getSweaters() + ")"));
                    closetCategoryItems.add(new ClosetCategoryItem("Glasses", "(" + categoryCount.getGlasses() + ")"));
                    closetCategoryItems.add(new ClosetCategoryItem("Jewries", "(" + categoryCount.getJewries() + ")"));
                    closetCategoryItems.add(new ClosetCategoryItem("Accessories", "(" + categoryCount.getAccessories() + ")"));
                    closetCategoryItems.add(new ClosetCategoryItem("Shoes", "(" + categoryCount.getShoes() + ")"));
                    closetCategoryItems.add(new ClosetCategoryItem("Underwear", "(" + categoryCount.getUnderwear() + ")"));

                    binding.proBar.setVisibility(View.GONE);
                    adapter.submitList(closetCategoryItems);
                    binding.sorry.setVisibility(View.GONE);
                }else {
                    binding.sorry.setVisibility(View.VISIBLE);
                    binding.proBar.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }
}