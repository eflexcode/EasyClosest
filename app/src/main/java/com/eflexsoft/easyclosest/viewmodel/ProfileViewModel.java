package com.eflexsoft.easyclosest.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.eflexsoft.easyclosest.repository.ProfileRepository;

public class ProfileViewModel extends AndroidViewModel {

    ProfileRepository repository;

    public ProfileViewModel(@NonNull Application application) {
        super(application);

        repository = new ProfileRepository(application);

    }

    public void updateProfile(String filed, String value) {
        repository.updateProfile(filed, value);
    }

    public void uploadImageUri(Uri uri, String oldImage) {
        repository.uploadImageUri(uri, oldImage);
    }
}
