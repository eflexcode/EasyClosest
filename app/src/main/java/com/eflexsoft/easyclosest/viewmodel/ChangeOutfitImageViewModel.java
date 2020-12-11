package com.eflexsoft.easyclosest.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.eflexsoft.easyclosest.repository.ChangeOutfitImageRepository;

public class ChangeOutfitImageViewModel extends AndroidViewModel {

    ChangeOutfitImageRepository repository;

    public ChangeOutfitImageViewModel(@NonNull Application application) {
        super(application);
        repository = new ChangeOutfitImageRepository(application);
    }

    public void changeImage(String oldUrl, String name, long id, int position, Uri uri) {
        repository.changeImage(oldUrl, name, id, position, uri);
    }

}
