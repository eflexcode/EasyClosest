package com.eflexsoft.easyclosest.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.easyclosest.repository.ClosetItemRepository;

public class ClosetItemViewModel extends AndroidViewModel {

    ClosetItemRepository repository;

    public MutableLiveData<Boolean> isDelete = new MutableLiveData<>();
    public MutableLiveData<Boolean> isDeleteInSuccessful = new MutableLiveData<>();

    public ClosetItemViewModel(@NonNull Application application) {
        super(application);

        repository = new ClosetItemRepository(application);

    }

    public LiveData<Boolean> getIsDelete() {

        return isDelete;
    }

    public LiveData<Boolean> getIsDeleteInSuccessful() {

        isDeleteInSuccessful = repository.isDeleteInSuccessful;

        return isDeleteInSuccessful;
    }

    public void deleteItem(String category, String id) {
        repository.deleteItem(category, id);
    }

}
