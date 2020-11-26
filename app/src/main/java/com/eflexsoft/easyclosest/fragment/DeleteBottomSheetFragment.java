package com.eflexsoft.easyclosest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;


import com.eflexsoft.easyclosest.R;
import com.eflexsoft.easyclosest.databinding.DeleteBottomSheetLayoutBinding;
import com.eflexsoft.easyclosest.viewmodel.ClosetItemViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class DeleteBottomSheetFragment extends BottomSheetDialogFragment {
    ClosetItemViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        DeleteBottomSheetLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.delete_bottom_sheet_layout, container, false);

        View view = binding.getRoot();

        viewModel = new ViewModelProvider(getActivity()).get(ClosetItemViewModel.class);

        binding.confirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                viewModel.isDelete.setValue(true);
            }
        });

        binding.nope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                viewModel.isDelete.setValue(false);

            }
        });

        return view;
    }
}
