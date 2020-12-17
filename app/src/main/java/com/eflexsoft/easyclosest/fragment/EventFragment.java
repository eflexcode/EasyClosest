package com.eflexsoft.easyclosest.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eflexsoft.easyclosest.R;
import com.eflexsoft.easyclosest.databinding.FragmentEventBinding;


public class EventFragment extends Fragment {

    FragmentEventBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_event, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event, container, false);

        View view = binding.getRoot();


        return view;
    }
}