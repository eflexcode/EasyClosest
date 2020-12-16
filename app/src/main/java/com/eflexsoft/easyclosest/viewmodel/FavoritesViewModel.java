package com.eflexsoft.easyclosest.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.easyclosest.model.ClosetItem;
import com.eflexsoft.easyclosest.repository.FavoritesRepository;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

    FavoritesRepository repository;
    public MutableLiveData<List<ClosetItem>> mutableLiveData = new MutableLiveData<>();

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        repository = new FavoritesRepository(application);
        repository.getAllFavorite();
    }

    public LiveData<List<ClosetItem>> closetItemLiveData() {

        mutableLiveData = repository.liveData;

        return mutableLiveData;
    }
}
