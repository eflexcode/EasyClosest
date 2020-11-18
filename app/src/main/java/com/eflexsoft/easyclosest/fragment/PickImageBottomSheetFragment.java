package com.eflexsoft.easyclosest.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.eflexsoft.easyclosest.AddToClosetActivity;
import com.eflexsoft.easyclosest.AddToDailyOutfitActivity;
import com.eflexsoft.easyclosest.R;
import com.eflexsoft.easyclosest.databinding.PickImageBottomSheetLayoutBinding;
import com.eflexsoft.easyclosest.databinding.UploadBottomSheetLayoutBinding;
import com.eflexsoft.easyclosest.viewmodel.PickImageViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class PickImageBottomSheetFragment extends BottomSheetDialogFragment {

    PickImageViewModel viewModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.upload_bottom_sheet_layout, container, false);

        PickImageBottomSheetLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.pick_image_bottom_sheet_layout, container, false);

        viewModel = new ViewModelProvider(getActivity()).get(PickImageViewModel.class);

        View view = binding.getRoot();

        binding.gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                viewModel.isCamera.setValue(false);
            }
        });

        binding.camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                viewModel.isCamera.setValue(true);
            }
        });

        return view;
    }
}
