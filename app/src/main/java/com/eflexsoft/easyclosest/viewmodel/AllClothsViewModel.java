package com.eflexsoft.easyclosest.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.easyclosest.model.CategoryCount;
import com.eflexsoft.easyclosest.repository.AllClothsRepository;

public class AllClothsViewModel extends AndroidViewModel {

    AllClothsRepository repository;

    public MutableLiveData<CategoryCount> categoryCountMutableLiveData = new MutableLiveData<>();

    public AllClothsViewModel(@NonNull Application application) {
        super(application);

        repository = new AllClothsRepository(application);

    }

    public LiveData<CategoryCount> getCategoryCountMutableLiveData() {
        categoryCountMutableLiveData = repository.categoryCountMutableLiveData;

        return categoryCountMutableLiveData;
    }
}
