package com.eflexsoft.easyclosest.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.easyclosest.model.User;
import com.eflexsoft.easyclosest.repository.HomeFragmentRepository;

public class HomeFragmentViewModel extends AndroidViewModel {

    HomeFragmentRepository repository;
    public MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

    public HomeFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = new HomeFragmentRepository(application);
    }

    public void getUserDetails() {
        repository.getUserDetails();
    }

    public LiveData<User> getUserMutableLiveData() {
        userMutableLiveData = repository.userMutableLiveData;
        return userMutableLiveData;
    }

}
