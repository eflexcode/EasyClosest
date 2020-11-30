package com.eflexsoft.easyclosest.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.eflexsoft.easyclosest.repository.AddToDailyOutfitRepository;

import java.util.List;

public class AddToDailyOutfitViewModel extends AndroidViewModel {

    AddToDailyOutfitRepository repository;

    public AddToDailyOutfitViewModel(@NonNull Application application) {
        super(application);

        repository = new AddToDailyOutfitRepository(application);

    }
    public void addToOutfit(List<Uri> uriList, String season, String note, String date) {
        repository.addToOutfit(uriList, season, note, date);
    }


}
