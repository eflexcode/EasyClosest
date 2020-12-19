package com.eflexsoft.easyclosest.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AdsViewModel extends AndroidViewModel {

  public MutableLiveData<Boolean> showAds = new MutableLiveData<>();

    public AdsViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<Boolean> showAds(){

        return showAds;
    }
}
