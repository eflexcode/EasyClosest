package com.eflexsoft.easyclosest.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.easyclosest.repository.ChangeOutfitTextRepository;

public class ChangeOutfitTextViewModel extends AndroidViewModel {

    ChangeOutfitTextRepository repository;
    public MutableLiveData<Boolean> isUploadInSuccessful = new MutableLiveData<>();

    public ChangeOutfitTextViewModel(@NonNull Application application) {

        super(application);

        repository = new ChangeOutfitTextRepository(application);
    }

    public void UpdateTet(String id, String note, String date, String season) {
        repository.UpdateTet(id, note, date, season);
    }

    public LiveData<Boolean> getIsUploadInSuccessful() {

        isUploadInSuccessful = repository.isUploadInSuccessful;

        return isUploadInSuccessful;
    }
}
