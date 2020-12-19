package com.eflexsoft.easyclosest.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eflexsoft.easyclosest.R;
import com.eflexsoft.easyclosest.databinding.FragmentProfileBinding;
import com.eflexsoft.easyclosest.model.User;
import com.eflexsoft.easyclosest.viewmodel.HomeFragmentViewModel;
import com.eflexsoft.easyclosest.viewmodel.ProfileViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.kroegerama.imgpicker.BottomSheetImagePicker;
import com.kroegerama.imgpicker.ButtonType;

import java.util.List;

public class ProfileFragment extends Fragment implements BottomSheetImagePicker.OnImagesSelectedListener {

    FragmentProfileBinding binding;
    HomeFragmentViewModel viewModel;
    ProfileViewModel profileViewModel;
    Dialog dialog;
    String updateFiled;
    User user2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_profile, container, false);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        View view = binding.getRoot();

        viewModel = new ViewModelProvider(this)
                .get(HomeFragmentViewModel.class);

        profileViewModel = new ViewModelProvider(this)
                .get(ProfileViewModel.class);
        viewModel.getUserDetails();
        viewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.setUser(user);
                user2 = user;
            }
        });

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.update_profile_layout);

        Button update = dialog.findViewById(R.id.update);
        Button cancel = dialog.findViewById(R.id.cancel);
        EditText editText = dialog.findViewById(R.id.text_t);

        binding.n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setHint("Username");
                updateFiled = "username";
                editText.setText(binding.name.getText().toString());
                dialog.show();
            }
        });

        binding.camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BottomSheetImagePicker.Builder(getString(R.string.file_provider))
                        .cameraButton(ButtonType.Button)
                        .galleryButton(ButtonType.Button)
                        .singleSelectTitle(R.string.pick_single)
                        .peekHeight(R.dimen.peekHeight)
                        .columnSize(R.dimen.columnSize)
                        .requestTag("single")
                        .show(getChildFragmentManager(), "gg");
            }
        });

        binding.e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setHint("Email");
                updateFiled = "email";
                editText.setText(binding.email.getText().toString());
                dialog.show();
            }
        });

        binding.a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setHint("Age");
                updateFiled = "age";
                editText.setText(binding.age.getText().toString());
                dialog.show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileViewModel.updateProfile(updateFiled, editText.getText().toString());
                dialog.dismiss();
            }
        });

        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        AdRequest adRequest = new AdRequest.Builder()
                .tagForChildDirectedTreatment(true)
                .build();

        binding.adView.loadAd(adRequest);

        return view;

    }

    @Override
    public void onImagesSelected(List<? extends Uri> list, String s) {

        profileViewModel.uploadImageUri(list.get(0), user2.getProfilePictureUlr());

        Toast.makeText(getContext(), "Uploading", Toast.LENGTH_SHORT).show();

    }
}