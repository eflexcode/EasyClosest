package com.eflexsoft.easyclosest.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PickImageViewModel extends AndroidViewModel {

  public MutableLiveData<Boolean> isCamera = new MutableLiveData<>();

    public PickImageViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<Boolean> getIsCamera(){

        return isCamera;
    }
}
