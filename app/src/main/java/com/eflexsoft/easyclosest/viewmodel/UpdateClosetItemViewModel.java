package com.eflexsoft.easyclosest.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.easyclosest.repository.UpdateClosetItemRepository;

public class UpdateClosetItemViewModel extends AndroidViewModel {

    UpdateClosetItemRepository repository;
    MutableLiveData<Boolean> isUpdateSuccessful = new MutableLiveData<>();

    public UpdateClosetItemViewModel(@NonNull Application application) {
        super(application);

        repository = new UpdateClosetItemRepository(application);

    }

    public void updateWithStringImage(long id, String category, String season, String note) {
        repository.updateWithStringImage(id, category, season, note);
    }

    public void uploadImageByte(long id, byte[] bytes, String category, String season, String note) {
        repository.uploadImageByte(id, bytes, category, season, note);
    }

    public void uploadImageUri(Uri uri, String category, String season, String note, long id) {
        repository.uploadImageUri(uri, category, season, note, id);
    }

    public LiveData<Boolean> getIsUpdateSuccessful() {

        isUpdateSuccessful = repository.isUpdateSuccessful;

        return isUpdateSuccessful;
    }
}
