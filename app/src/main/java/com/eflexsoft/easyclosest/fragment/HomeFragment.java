package com.eflexsoft.easyclosest.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eflexsoft.easyclosest.R;
import com.eflexsoft.easyclosest.adapter.ViewPaggerAdapter;
import com.eflexsoft.easyclosest.databinding.FragmentHomeBinding;
import com.eflexsoft.easyclosest.model.User;
import com.eflexsoft.easyclosest.viewmodel.HomeFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    ViewPaggerAdapter adapter;
    HomeFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentHomeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

//        return inflater.inflate(R.layout.fragment_home, container, false);

        View view = binding.getRoot();

        List<String> strings = new ArrayList<>();
        strings.add("My Closet");
        strings.add("Daily Outfit");

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new AllClothsFragment());
        fragments.add(new DailyClothsFragment());

        adapter = new ViewPaggerAdapter(getChildFragmentManager(), strings, fragments);

        binding.pager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.pager);

        viewModel = new ViewModelProvider(this)
                .get(HomeFragmentViewModel.class);
        viewModel.getUserDetails();
        viewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.setUser(user);
            }
        });

        return view;
    }
}