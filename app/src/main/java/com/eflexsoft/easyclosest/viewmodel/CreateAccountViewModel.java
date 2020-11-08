package com.eflexsoft.easyclosest.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.easyclosest.repository.CreateAccountRepository;
import com.eflexsoft.easyclosest.repository.LoginRepository;
import com.google.firebase.auth.AuthCredential;

public class CreateAccountViewModel extends AndroidViewModel {

    CreateAccountRepository repository;
    public MutableLiveData<Boolean> isSignInSuccessful = new MutableLiveData<>();

    public CreateAccountViewModel(@NonNull Application application) {
        super(application);
        repository = new CreateAccountRepository(application);
    }

    public void createAccount(String email, String name, String password) {
        repository.createAccount(email, name, password);
    }

    public LiveData<Boolean> isSuccess() {
        isSignInSuccessful = repository.isSignInSuccessful;

        return isSignInSuccessful;
    }

    public void doGoogleSignIn(AuthCredential authCredential) {
        repository.doGoogleSignIn(authCredential);
    }
}
