package com.eflexsoft.easyclosest.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.easyclosest.repository.AddToClosetRepository;

public class AddToClosetViewModel extends AndroidViewModel {

    public MutableLiveData<Boolean> isUploadInSuccessful = new MutableLiveData<>();

    AddToClosetRepository repository;

    public AddToClosetViewModel(@NonNull Application application) {
        super(application);

        repository = new AddToClosetRepository(application);

    }

    public LiveData<Boolean> isSuccess() {
        isUploadInSuccessful = repository.isUploadInSuccessful;

        return isUploadInSuccessful;
    }

    public void uploadImageByte(byte[] bytes, String category, String season, String note) {
        repository.uploadImageByte(bytes, category, season, note);
    }

    public void uploadImageUri(Uri uri, String category, String season, String note) {
        repository.uploadImageUri(uri, category, season, note);
    }
    public void doAddToFavorite(String id, String category,boolean isFavourite) {
        repository.doAddToFavorite(id, category,isFavourite);
    }
}
