package com.eflexsoft.easyclosest.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.eflexsoft.easyclosest.AddToClosetActivity;
import com.eflexsoft.easyclosest.AddToDailyOutfitActivity;
import com.eflexsoft.easyclosest.R;
import com.eflexsoft.easyclosest.databinding.EditOufitBottomSheetLayoutBinding;
import com.eflexsoft.easyclosest.databinding.UploadBottomSheetLayoutBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class EditBottomSheetFragment extends BottomSheetDialogFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.upload_bottom_sheet_layout, container, false);

        EditOufitBottomSheetLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.edit_oufit_bottom_sheet_layout, container, false);

        View view = binding.getRoot();

        binding.editImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
//                startActivity(new Intent(getContext(), AddToClosetActivity.class));
            }
        });

        binding.editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
//                startActivity(new Intent(getContext(), AddToDailyOutfitActivity.class));

            }
        });

        return view;
    }
}
