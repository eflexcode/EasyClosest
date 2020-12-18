package com.eflexsoft.easyclosest.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.eflexsoft.easyclosest.repository.AddToDailyOutfitRepository;
import com.eflexsoft.easyclosest.repository.EventRepository;

import java.util.List;

public class EventViewModel extends AndroidViewModel {

    EventRepository repository;

    public EventViewModel(@NonNull Application application) {
        super(application);

        repository = new EventRepository(application);

    }

    public void addToEvent(List<Uri> uriList, String note, String date) {
        repository.addToEvent(uriList, note, date);
    }

    public void delete(String id) {
        repository.delete(id);
    }

    public void changeImage(String oldUrl, String name, long id, int position, Uri uri) {
        repository.changeImage(oldUrl, name, id, position, uri);
    }
    public void UpdateTet(String id, String note, String date) {
        repository.UpdateTet(id, note, date);
    }
}
