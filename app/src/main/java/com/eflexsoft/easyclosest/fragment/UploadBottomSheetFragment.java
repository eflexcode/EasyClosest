package com.eflexsoft.easyclosest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eflexsoft.easyclosest.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class UploadBottomSheetFragment extends BottomSheetDialogFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL,R.style.AppBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upload_bottom_sheet_layout, container, false);

        return view;
    }
}
