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
import com.eflexsoft.easyclosest.databinding.FragmentProfileBinding;
import com.eflexsoft.easyclosest.model.User;
import com.eflexsoft.easyclosest.viewmodel.HomeFragmentViewModel;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    HomeFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_profile, container, false);

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile,container,false);

        View view = binding.getRoot();

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